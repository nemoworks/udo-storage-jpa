package info.nemoworks.udo.repository.jpa.entity;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.HashMap;
import java.util.Map;

@Entity
public class FlattenEntity {

    @Id
    protected String id;

    private long createdOn;
    private String createdBy;
    private long modifiedOn;
    private String modifiedBy;
    private String uri;
//
//    @OneToOne(targetEntity = MetaInfo.class, cascade = CascadeType.ALL)
//    private MetaInfo metaInfo;

    @Convert(converter = Converter.class)
    private Map<String, Object> tuples = new HashMap<>();

    public FlattenEntity() {

    }

    public FlattenEntity(String id, Map<String, Object> tuples) {
        this.id = id;
        this.tuples = tuples;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Map<String, Object> getTuples() {
        return tuples;
    }

    public void setTuples(Map<String, Object> tuples) {
        this.tuples = tuples;
    }

    public void printTuples() {
        this.tuples.forEach((key, value) -> System.out.println(key + ": " + value));
    }

    public long getCreatedOn() {
        return createdOn;
    }

    public long getModifiedOn() {
        return modifiedOn;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public String getUri() {
        return uri;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public void setCreatedOn(long createdOn) {
        this.createdOn = createdOn;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public void setModifiedOn(long modifiedOn) {
        this.modifiedOn = modifiedOn;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}

