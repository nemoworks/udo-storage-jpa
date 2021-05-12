package info.nemoworks.udo.repository.jpa.entity;

import java.util.Map;

import javax.persistence.Entity;

import com.github.wnameless.json.flattener.JsonFlattener;

import org.springframework.context.annotation.Lazy;

import info.nemoworks.udo.model.Udo;

@Entity
public class UdoEntity extends FlattenEntity {

    @Lazy
    private TypeEntity typeEntity;

    public UdoEntity() {
        super();

    }

    public UdoEntity(String id, TypeEntity typeEntity, Map<String, Object> tuples) {
        super(id, tuples);
        this.typeEntity = typeEntity;
    }

    public TypeEntity getTypeEntity() {
        return typeEntity;
    }

    public void setTypeEntity(TypeEntity typeEntity) {
        this.typeEntity = typeEntity;
    }

    public static UdoEntity fromUdo(Udo udo) {
       
        UdoEntity udoEntity = new UdoEntity();
        TypeEntity typeEntity = new TypeEntity();

        typeEntity.setId(udo.getType().getId());
        typeEntity.setTuples(JsonFlattener.flattenAsMap(udo.getType().getSchema().getAsString()));
        

        udoEntity.setId(udo.getId());
        udoEntity.setTypeEntity(typeEntity);
        udoEntity.setTuples(JsonFlattener.flattenAsMap(udo.getData().getAsString()));

        return udoEntity;

    }

    public Udo toUdo() {
       
        return null;
    }
}
