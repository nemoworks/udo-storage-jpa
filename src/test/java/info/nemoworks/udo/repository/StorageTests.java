package info.nemoworks.udo.repository;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import info.nemoworks.udo.repository.manager.Translate;
import net.sf.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

//@SpringBootTest(classes = {Translate.class})
class StorageTests {

    public String loadFromFile() throws IOException {
        return new String(Files.readAllBytes(Paths.get("src/test/resources/test0.json")));
    }

    @Test
    void contextLoads() {
    }

    @Test
    public void translatingTest() throws IOException {
//        JSONObject obj = JSONObject.fromObject(this.loadFromFile());
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode obj = objectMapper.readTree(this.loadFromFile());
//        obj.put("arr", "{[{val: 1}, {val: 2}]}");
//        obj.put("obj", "{a: {b: c}}");
//        for (Map.Entry entry: obj.entrySet()) {
//            System.out.println(entry.getValue().getClass());
//            System.out.println(entry.getValue() instanceof JSONArray);
//            System.out.println(entry.getValue() instanceof JsonObject);
//        }
        Translate translate = new Translate((ObjectNode) obj);
        translate.startTrans();
        System.out.println(translate.getUTuples());
        translate.printTuples();
        translate.startBackTrans();
        System.out.println(translate.getobjectNode());
    }

}
