package info.nemoworks.udo.repository.jpa.entity;

import com.github.wnameless.json.flattener.JsonFlattener;
import com.github.wnameless.json.unflattener.JsonUnflattener;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import info.nemoworks.udo.model.UdoType;

import javax.persistence.Entity;

//import info.nemoworks.udo.model.MetaInfo;

@Entity
public class TypeEntity extends FlattenEntity {

    public static TypeEntity from(UdoType udoType) {

        TypeEntity type = new TypeEntity();
        type.setId(udoType.getId());
        type.setTuples(JsonFlattener.flattenAsMap(new Gson().toJson(udoType.getSchema())));
//        type.setMetaInfo(udoType.getMetaInfo());
        type.setCreatedBy(udoType.createdBy);
        return type;

    }

    public UdoType toUdoType() {

        String unFlattenedType = JsonUnflattener.unflatten(this.getTuples());
        JsonObject type = new Gson().fromJson(unFlattenedType, JsonObject.class);
        UdoType udoType = new UdoType(type);
        udoType.setId(id);
//        udoType.setMetaInfo(this.getMetaInfo());
        udoType.setCreatedBy(getCreatedBy());
//        udoType.setMetaInfo(new MetaInfo() {{
//            this.createdBy = getCreatedBy();
//        }});
        return udoType;
    }
}
