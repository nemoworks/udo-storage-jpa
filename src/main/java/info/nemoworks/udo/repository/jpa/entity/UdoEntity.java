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

    @OneToMany(targetEntity = UTuple.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<UTuple> UTuples;

    public UdroDocument() {

    }

    public UdroDocument(List<UTuple> UTuples, String firstTableName, String secondTableName) {
        this.UTuples = UTuples;
        this.firstTableName = firstTableName;
        this.secondTableName = secondTableName;
        this.tableName = firstTableName + "_" + secondTableName;
    }

    public void setUTuples(List<UTuple> UTuples) {
        this.UTuples = UTuples;
    }

    public List<UTuple> getUTuples() {
        return UTuples;
    }

    public String getFirstTableName() {
        return firstTableName;
    }

    public String getSecondTableName() {
        return secondTableName;
    }

    public void setFirstTableName(String firstTableName) {
        this.firstTableName = firstTableName;
    }

    public void setSecondTableName(String secondTableName) {
        this.secondTableName = secondTableName;
    }

    public String getTableName() {
        return tableName;
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
