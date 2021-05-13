package info.nemoworks.udo.repository.jpa.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Map;

@Entity
public class FlattenEntity {

    @Id
    protected String id;

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

    public void printTuples() {
        this.tuples.forEach((key, value) -> System.out.println(key + ": " + value));
    }

}
