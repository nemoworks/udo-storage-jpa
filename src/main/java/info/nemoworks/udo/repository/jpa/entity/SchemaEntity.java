package info.nemoworks.udo.repository.jpa.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import info.nemoworks.udo.model.Udo;
import info.nemoworks.udo.model.UdoSchema;

@Entity
public class SchemaEntity {
    @Id
    @GeneratedValue
    private int pkey;

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
        // todo
        return null;
    }

    public UdoSchema tUdoSchema() {
        // todo
        return null;
    }
}
