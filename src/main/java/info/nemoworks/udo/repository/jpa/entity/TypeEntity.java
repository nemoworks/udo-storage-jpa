package info.nemoworks.udo.repository.jpa.entity;

import javax.persistence.Entity;

import com.github.wnameless.json.flattener.JsonFlattener;

import info.nemoworks.udo.model.UdoType;

@Entity
public class TypeEntity extends FlattenEntity {

    public static TypeEntity from(UdoType udoType) {

        TypeEntity type = new TypeEntity();
        type.setId(udoType.getId());
        type.setTuples(JsonFlattener.flattenAsMap(udoType.getSchema().getAsString()));
        return type;

    }

    public UdoType toUdoType() {
        // Translate translate = new Translate(this.getTupleEntitys());
        // translate.startBackTrans();
        // UdoSchema udoSchema = new UdoSchema(translate.getObjectNode());
        // udoSchema.setId(this.schemaId);
        // return udoSchema;
        return null;
    }
}
