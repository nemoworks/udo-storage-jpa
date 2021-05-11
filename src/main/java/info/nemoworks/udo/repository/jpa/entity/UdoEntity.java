package info.nemoworks.udo.repository.jpa.entity;

import javax.persistence.*;

import org.springframework.context.annotation.Lazy;

import info.nemoworks.udo.model.Udo;

import java.util.List;

/*
Udro: Universal Digital Relational Object
 */

@javax.persistence.Entity
public class UdoEntity {
    @Id
    @GeneratedValue
    private Long pkey;

    private String udoId;

    @Lazy
    private SchemaEntity schemaEntity;

    @OneToMany(targetEntity = TupleEntity.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<TupleEntity> TupleEntitys;

    public UdoEntity() {

    }

    public UdoEntity(List<TupleEntity> TupleEntitys, String udoId, SchemaEntity schemaEntity) {
        this.TupleEntitys = TupleEntitys;
        this.udoId = udoId;
        this.schemaEntity = schemaEntity;
//        this.firstTableName = firstTableName;
//        this.secondTableName = secondTableName;
//        this.tableName = firstTableName + "_" + secondTableName;
    }

    public void setTupleEntitys(List<TupleEntity> TupleEntitys) {
        this.TupleEntitys = TupleEntitys;
    }

    public List<TupleEntity> getTupleEntitys() {
        return TupleEntitys;
    }


    // public String getTableName() {
    // return tableName;
    // }
    //
    // public void setTableName(String tableName) {
    // this.tableName = tableName;
    // }

    public static UdoEntity fromUdo(Udo udo) {
        // todo
        return null;
    }

    public Udo toUdo() {
        // todo
        return null;
    }
}
