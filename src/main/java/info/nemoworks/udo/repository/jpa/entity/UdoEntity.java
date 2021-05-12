package info.nemoworks.udo.repository.jpa.entity;

import javax.persistence.*;

import com.fasterxml.jackson.databind.node.ObjectNode;
import info.nemoworks.udo.model.UdoSchema;
import org.springframework.context.annotation.Lazy;

import info.nemoworks.udo.model.Udo;

import java.util.List;

/*
Udro: Universal Digital Relational Object
 */

@javax.persistence.Entity
public class UdoEntity {
    @Id
    private String udoId;

    @Lazy
    private SchemaEntity schemaEntity;

    @OneToMany(targetEntity = TupleEntity.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<TupleEntity> TupleEntitys;

    public UdoEntity() {

    }

    public UdoEntity(String udoId, SchemaEntity schemaEntity, List<TupleEntity> TupleEntitys) {
        this.TupleEntitys = TupleEntitys;
        this.udoId = udoId;
        this.schemaEntity = schemaEntity;
    }

    public void setTupleEntitys(List<TupleEntity> TupleEntitys) {
        this.TupleEntitys = TupleEntitys;
    }

    public List<TupleEntity> getTupleEntitys() {
        return TupleEntitys;
    }

    public String getUdoId() {
        return udoId;
    }

    public void setUdoId(String udoId) {
        this.udoId = udoId;
    }

    public SchemaEntity getSchemaEntity() {
        return schemaEntity;
    }

    public void setSchemaEntity(SchemaEntity schemaEntity) {
        this.schemaEntity = schemaEntity;
    }

    public static UdoEntity fromUdo(Udo udo) {
        Translate translateData = new Translate((ObjectNode) udo.getData());
        Translate translateSchema = new Translate((ObjectNode) udo.getSchema().getSchema());
        translateData.startTrans();
        translateSchema.startTrans();
        String udoId = udo.getId();
        List<TupleEntity> dataTupleEntities = translateData.getTupleEntitys();
        String schemaId = udo.getSchema().getId();
        List<TupleEntity> schemaTupleEntities = translateSchema.getTupleEntitys();
        SchemaEntity schemaEntity = new SchemaEntity(schemaId, schemaTupleEntities);
        return new UdoEntity(udoId, schemaEntity, dataTupleEntities);
    }

    public Udo toUdo() {
        Translate translateData = new Translate(this.TupleEntitys);
        Translate translateSchema = new Translate(this.schemaEntity.getTupleEntitys());
        translateData.startBackTrans();
        translateSchema.startBackTrans();
        String udoId = this.udoId;
        UdoSchema udoSchema = new UdoSchema(translateSchema.getObjectNode());
        udoSchema.setId(this.schemaEntity.getSchemaId());
        Udo udo = new Udo(udoSchema, translateData.getObjectNode());
        udo.setId(udoId);
        return udo;
    }
}
