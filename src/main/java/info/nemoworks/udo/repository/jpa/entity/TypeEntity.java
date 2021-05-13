package info.nemoworks.udo.repository.jpa.entity;

import com.github.wnameless.json.flattener.JsonFlattener;
import com.github.wnameless.json.unflattener.JsonUnflattener;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import info.nemoworks.udo.model.UdoType;

import javax.persistence.Entity;

@Entity
public class TypeEntity extends FlattenEntity {

    public static TypeEntity from(UdoType udoType) {

        TypeEntity type = new TypeEntity();
        type.setId(udoType.getId());
        type.setTuples(JsonFlattener.flattenAsMap(udoType.getSchema().getAsString()));
        return type;

    }

    public UdoType toUdoType() {

        String unFlattenedType = JsonUnflattener.unflatten(this.getTuples());
        JsonObject type = new Gson().fromJson(unFlattenedType, JsonObject.class);
        UdoType udoType = new UdoType(type);
        udoType.setId(id);
        return udoType;
    }
}
