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

    private String tableName;

    @OneToMany(targetEntity = UTuple.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<UTuple> UTuples;

    public UdroSchema() {

    }

    public UdroSchema(String tableName, List<UTuple> UTuples) {
        this.tableName = tableName;
        this.UTuples = UTuples;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public List<UTuple> getUTuples() {
        return UTuples;
    }

    public void setUTuples(List<UTuple> UTuples) {
        this.UTuples = UTuples;
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
