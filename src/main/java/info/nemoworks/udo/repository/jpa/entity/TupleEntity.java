package info.nemoworks.udo.repository.jpa.entity;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


@Entity
public class TupleEntity {
    @Id
    @GeneratedValue
    private int pkey;

    private int offset;

    private String name;
    
    private String val;

    public TupleEntity() {

    }

    public TupleEntity(int offset, String name, String val) {
        this.offset = offset;
        this.name = name;
        this.val = val;
    }

    public int getOffset() {
        return offset;
    }

    public String getName() {
        return name;
    }

    public String getVal() {
        return val;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public void setVal(String val) {
        this.val = val;
    }

    public void printTuple() {
        System.out.println(this.offset + " " + this.name + " " + this.val);
    }
}
