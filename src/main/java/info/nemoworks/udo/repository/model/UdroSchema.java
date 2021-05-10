package info.nemoworks.udo.repository.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class UdroSchema {
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
}
