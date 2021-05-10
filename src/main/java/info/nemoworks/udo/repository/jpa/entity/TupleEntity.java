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

    public UTuple() {

    }

    public UTuple(int uid, String name, String val) {
        this.uid = uid;
        this.name = name;
        this.val = val;
    }

    public int getUid() {
        return uid;
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

    public void setUid(int uid) {
        this.uid = uid;
    }

    public void setVal(String val) {
        this.val = val;
    }

    public void printTuple() {
        System.out.println(this.uid + " " + this.name + " " + this.val);
    }
}
