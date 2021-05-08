package info.nemoworks.udo.repository.manager;

import info.nemoworks.udo.repository.model.UTuple;
import net.sf.json.*;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Stack;

//import javafx.util.Pair;
//import org.json.JSONObject;

/*
Translate JSON to Tuples
 */
public class Translate {
    private List<UTuple> UTuples;
    private JSONObject JsonObject;
    private int uid;

    public Translate(JSONObject JsonObject) {
        this.UTuples = new ArrayList<>();
        this.uid = 0;
        this.JsonObject = JsonObject;
    }

    public Translate(List<UTuple> UTuples) {
        this.UTuples = UTuples;
        this.uid = 0;
        this.JsonObject = new JSONObject();
    }

    public void startTrans() {
        this.UTuples = new ArrayList<>();
        this.uid = 0;
        this.translatingObj(this.JsonObject, "");
    }

    public void startBackTrans() {
        this.JsonObject = new JSONObject();
        this.backTranslatingTuple(this.UTuples);
    }

    private void backTranslatingTuple(List<UTuple> uTuples) {
        for (UTuple uTuple : uTuples) {
            if (uTuple.getName().contains("[")) { // 当前Tuple为JsonArray
                backTranslatingArr(uTuple, uTuple.getName());
            }
            // 当前Tuple为纯Obj
            else if (uTuple.getName().contains(".")) backTranslatingObj(uTuple, uTuple.getName());
            // 当前Tuple为简单Element
            else {
                backTranslatingEle(uTuple);
            }
        }
    }

    private void backTranslatingEle(UTuple uTuple) {
        this.JsonObject.put(uTuple.getName(), uTuple.getVal());
    }

    /**
     *  逆转化Obj基本思路：找到a.b中的分割点"."，逐次将由"."分割的各个obj自下而上入栈
     *  最后通过出栈的方式完成从上至下的封装json的过程
     * @param uTuple
     * @param prefix
     */
    private void backTranslatingObj(UTuple uTuple, String prefix) {

        // 通过逆转String的方式获得分割点"."的位置
        StringBuffer sb = new StringBuffer(prefix);
        String reverse = sb.reverse().toString();
        int endIndex = prefix.length() - reverse.indexOf(".");
        String objName = prefix.substring(endIndex);
        JSONObject obj = new JSONObject();
        obj.put(objName, uTuple.getVal());
        String pPrefix = prefix.substring(0, endIndex - 1);
        // 两个栈分别存储当前object的key值即name与当前object的实际内容JSONObject
        Stack<String> nameStack = new Stack<>();
        Stack<JSONObject> objectStack = new Stack<>();
        nameStack.push(objName);
        objectStack.push(obj);
        while (!pPrefix.equals("") && !pPrefix.equals(pPrefix.substring(0, 0))) {
            sb = new StringBuffer(pPrefix);
            reverse = sb.reverse().toString();
            endIndex = pPrefix.length() - reverse.indexOf(".");
            if (!reverse.contains(".")) endIndex = 0; // 处理到根节点的情况
            objName = pPrefix.substring(endIndex);
            if (endIndex == 0) pPrefix = pPrefix.substring(0, 0);
            else pPrefix = pPrefix.substring(0, endIndex - 1);
            JSONObject nObj = new JSONObject();
            nObj.put(objName, obj);
            obj = nObj;
            nameStack.push(objName);
            objectStack.push(obj);
        }
        if (!this.JsonObject.containsKey(objName)) {
            // 如果当前key值为一个新值，表明是新节点加入，直接加进结果内即可
            this.JsonObject.put(objName, obj.getJSONObject(objName));
        } else {
            // 如果当前key值为一个旧值(已有值)，则需要找到当前分支在哪个后续节点添加了更新
            // 使用packUpObj()寻找到被更新的节点并打包返回完成更新
            JSONObject fatherObj = this.JsonObject.getJSONObject(objName); //获取JsonObject中已经存在的obj
            JSONObject lObj = new JSONObject();
            lObj.put(objName, fatherObj);
            JSONObject obj2put = packUpObj(lObj, nameStack, objectStack);
            // put方法会覆盖原有key，达到更新的目的
            this.JsonObject.put(objName, obj2put);
        }
    }

    /**
     * 递归，自下而上搭建新的JSONObj
     *
     * @param fatherObj 上层节点
     * @param nameStack 存储节点name
     * @param objStack  存储下层节点内容
     * @return 填充完成的Obj
     */
    private JSONObject packUpObj(JSONObject fatherObj, Stack<String> nameStack, Stack<JSONObject> objStack) {
        String curName = nameStack.pop();
        JSONObject curObj = objStack.pop();
        if (!fatherObj.containsKey(curName)) {
            // 此时找到了更新所在的节点，终止递归并回溯，打包成一个完整的JSONObj返回
            if (curObj.get(curName) instanceof String)
                fatherObj.put(curName, curObj.getString(curName));
            else fatherObj.put(curName, curObj.get(curName));
            return fatherObj;
        } else {
            // 仍未找到更新节点，继续向下递归
            return (JSONObject) fatherObj.put(curName, packUpObj(fatherObj.getJSONObject(curName), nameStack, objStack));
        }
    }

    /**
     * 逆转化Arr基本思路：JSON的Arr实际上逃不脱Obj的定义束缚
     * 实际上的Arr是{a : []}的形式，也即Arr可以看成Obj嵌套一个数组单元的形式
     * @param uTuple
     * @param prefix
     */
    private void backTranslatingArr(UTuple uTuple, String prefix) {
        // 同上，寻找分割点
        StringBuffer sb = new StringBuffer(prefix);
        String reverse = sb.reverse().toString();
        int endIndex = prefix.length() - reverse.indexOf(".");
        String objName = prefix.substring(endIndex);
        JSONObject obj = new JSONObject();
        obj.put(objName, uTuple.getVal());
        String pPrefix = prefix.substring(0, endIndex - 1);
        Stack<String> nameStack = new Stack<>();
        // 此时的SimpleEntry是JDK 12的用法, 与JDK 8的Pair同理，构成键值对
        // 用来指示当前处理的节点是JSONObject还是JSONArray类型
        Stack<SimpleEntry<JSONObject, String>> objectStack = new Stack<>();
        nameStack.push(objName);
        objectStack.push(new SimpleEntry<>(obj, "Object"));
        // 考虑 Object 与 Array 互相嵌套的 5 种情况, 由于[]符号的加入, 情况判断变得复杂
        while (!pPrefix.equals("") && !pPrefix.equals(pPrefix.substring(0, 0))) {
            sb = new StringBuffer(pPrefix);
            reverse = sb.reverse().toString();
            int indexArr = reverse.indexOf("]");
            int indexLeftArr = reverse.indexOf("[");
            int indexDot = reverse.indexOf(".");
            if (indexArr == -1 && indexDot == -1) { // 根节点 形如a
                String ObjName = pPrefix;
                nameStack.push(ObjName);
                pPrefix = pPrefix.substring(0, 0);
                JSONObject JsonObject = new JSONObject();
                JsonObject.put(ObjName, obj);
                objectStack.push(new SimpleEntry<>(JsonObject, "Object"));
                obj = JsonObject;
                objName = ObjName;
            } else if (indexDot == -1) { // 根节点，形如a[x]
                String ArrName = pPrefix.substring(0, pPrefix.indexOf("["));
                nameStack.push(ArrName);
                pPrefix = pPrefix.substring(0, 0);
                JSONArray jsonArray = new JSONArray();
                jsonArray.add(obj);
                obj = new JSONObject();
                objName = ArrName;
                obj.put(objName, jsonArray);
                objectStack.push(new SimpleEntry<>(obj, "Array"));
            } else if (indexArr == -1) { // 形如 a.b.c
                endIndex = pPrefix.length() - reverse.indexOf(".");
                String ObjName = pPrefix.substring(endIndex);
                pPrefix = pPrefix.substring(0, endIndex - 1);
                JSONObject JsonObject = new JSONObject();
                nameStack.push(ObjName);
                JsonObject.put(ObjName, obj);
                objectStack.push(new SimpleEntry<>(JsonObject, "Object"));
                obj = JsonObject;
                objName = ObjName;
            } else if (indexArr < indexDot) { //形如 c.a[x]
                endIndex = pPrefix.length() - indexDot;
                String ArrName = pPrefix.substring(endIndex, pPrefix.length() - indexLeftArr - 1);
                pPrefix = pPrefix.substring(0, endIndex - 1);
                nameStack.push(ArrName);
                JSONArray JsonArray = new JSONArray();
                JsonArray.add(obj);
                obj = new JSONObject();
                objName = ArrName;
                ((JSONObject) obj).put(objName, JsonArray);
                objName = ArrName;
                objectStack.push(new SimpleEntry<>(obj, "Array"));
            } else { //形如 a[x].c
                endIndex = pPrefix.length() - indexArr + 1;
                String ObjName = pPrefix.substring(endIndex);
                pPrefix = pPrefix.substring(0, endIndex - 1);
                nameStack.push(ObjName);
                JSONObject JsonObject = new JSONObject();
                JsonObject.put(ObjName, obj);
                objectStack.push(new SimpleEntry<>(JsonObject, "Object"));

                obj = JsonObject;
                objName = ObjName;
            }
        }
        if (!this.JsonObject.containsKey(objName)) {
            // 如果当前节点的key为新值，直接将其插入进结果obj
            this.JsonObject.put(objName, ((JSONObject) obj).get(objName));
        } else {
            // 反之，我们使用packUpArray()来完成寻找更新分支并递归打包完成更新的过程
            Object fatherObj = this.JsonObject.get(objName);
            JSONObject lObj = new JSONObject();
            lObj.put(objName, fatherObj);
            JSONObject obj2put = packUpArray(lObj, nameStack, objectStack);
            this.JsonObject.put(objName, obj2put.get(objName));
        }
    }

    private JSONObject packUpArray(JSONObject fatherObj, Stack<String> nameStack, Stack<SimpleEntry<JSONObject, String>> objStack) {
        String curName = nameStack.pop();
        SimpleEntry<JSONObject, String> curPair = objStack.pop();
        String curType = curPair.getValue();
        if (curType.equals("Object")) { // 当前节点为JsonObject
            if (!(fatherObj).containsKey(curName)) { // 节点中不包含此层定义，说明是新加入的内容，直接添加并返回
                JSONObject curObj = curPair.getKey();
                fatherObj.put(curName, curObj.get(curName));
                return fatherObj;
            } else { // 向下递归
                fatherObj.put(curName, packUpArray(fatherObj.getJSONObject(curName), nameStack, objStack));
                return fatherObj;
            }
        } else { // 当前节点为JsonArray
            JSONObject curObj = curPair.getKey();
            boolean exist = false;
            JSONObject switchObj = new JSONObject();
            int index = 0;
            String nextName;
            for (int i = 0; i < ((JSONArray) fatherObj.get(curName)).size(); i++) { // JsonArray的数组元素只可能是JsonObject
                // 因为就算是Array嵌套Array，也必定会经过一个a:[{b:[{}]}]的形式
                switchObj = (JSONObject) ((JSONArray) fatherObj.get(curName)).get(i);
                nextName = nameStack.pop();
                nameStack.push(nextName);
                if (((JSONObject) switchObj).containsKey(nextName)) {
                    exist = true;
                    index = i;
                    break;
                }
            }
            if (!exist) { // fatherObj代表的Array种不包含与当前处理的Obj节点同名的数组元素，则直接将当前节点加入Array
                ((JSONArray) fatherObj.get(curName)).add(curObj.get(curName));
                return fatherObj;
            } else { // fatherObj所代表的Array中存在已有定义，向下递归
                ((JSONArray) fatherObj.get(curName)).set(index, packUpArray(switchObj, nameStack, objStack));
                return fatherObj;
            }
        }
    }

    /**
     * translatingObj与translatingArr相互调用完成递归处理
     * 由于JSON的本质可以划分为Obj、Array以及基本条目Element来进行处理
     * 故此时我们只需考虑三种不同情况，分别处理节点即可
     * @param obj
     * @param suffix
     */
    private void translatingObj(JSONObject obj, String suffix) {
        String dot = ".";
        if (suffix.equals("")) dot = "";
        for (Object attr : obj.entrySet()) {
            Map.Entry entry = (Map.Entry) attr;
            if (entry.getValue() instanceof JSONObject) {
                translatingObj((JSONObject) entry.getValue(), suffix + dot + entry.getKey().toString());
            } else if (entry.getValue() instanceof JSONArray) {
                translatingArr((JSONArray) entry.getValue(), suffix + dot + entry.getKey().toString());
            } else {
                UTuples.add(new UTuple(this.uid, suffix + dot + entry.getKey().toString(), entry.getValue().toString()));
                this.uid++;
            }
        }
    }

    private void translatingArr(JSONArray arr, String suffix) {
        for (int i = 0; i < arr.size(); i++) {
            if (arr.get(i) instanceof JSONObject) {
                translatingObj((JSONObject) arr.get(i), suffix + "[" + String.valueOf(i) + "]");
            } else if (arr.get(i) instanceof JSONArray) {
                translatingArr((JSONArray) arr.get(i), suffix + "[" + String.valueOf(i) + "]");
            }
        }
    }

    public JSONObject getJsonObject() {
        return JsonObject;
    }

    public List<UTuple> getUTuples() {
        return UTuples;
    }

    public void setJsonObject(JSONObject JsonObject) {
        this.JsonObject = JsonObject;
    }

    public void setUTuples(List<UTuple> UTuples) {
        this.UTuples = UTuples;
    }

    public void printTuples() {
        for (UTuple UTuple : UTuples) {
            UTuple.printTuple();
        }
    }
}
