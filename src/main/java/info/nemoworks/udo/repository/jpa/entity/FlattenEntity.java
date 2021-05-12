package info.nemoworks.udo.repository.jpa.entity;

import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class FlattenEntity {

    @Id
    protected String id;

    //todo
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Map<String, Object> tuples;

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

}
