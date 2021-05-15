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

    //    @OneToMany
//    @ElementCollection
//    @CollectionTable(name = "attributes", joinColumns = @JoinColumn(name = "id"))
//    @MapKeyColumn(name = "key")
//    @Column(name = "value")
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

}
