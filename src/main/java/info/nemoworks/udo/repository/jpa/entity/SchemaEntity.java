package info.nemoworks.udo.repository.jpa.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.databind.node.ObjectNode;
import info.nemoworks.udo.model.UdoSchema;

@Entity
public class SchemaEntity {
    @Id
    private String schemaId;

    @OneToMany(targetEntity = TupleEntity.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<TupleEntity> TupleEntitys;

    public SchemaEntity() {

    }

    public SchemaEntity(String schemaId, List<TupleEntity> TupleEntitys) {
        this.schemaId = schemaId;
        this.TupleEntitys = TupleEntitys;
    }

    public String getSchemaId() {
        return schemaId;
    }

    public void setSchemaId(String schemaId) {
        this.schemaId = schemaId;
    }

    public List<TupleEntity> getTupleEntitys() {
        return TupleEntitys;
    }

    public void setTupleEntitys(List<TupleEntity> TupleEntitys) {
        this.TupleEntitys = TupleEntitys;
    }

    public static SchemaEntity from(UdoSchema schema) {
        Translate translate = new Translate((ObjectNode) schema.getSchema());
        String schemaId = schema.getId();
        translate.startTrans();
        return new SchemaEntity(schemaId, translate.getTupleEntitys());
    }

    public UdoSchema toUdoSchema() {
        Translate translate = new Translate(this.getTupleEntitys());
        translate.startBackTrans();
        UdoSchema udoSchema = new UdoSchema(translate.getObjectNode());
        udoSchema.setId(this.schemaId);
        return udoSchema;
    }
}
