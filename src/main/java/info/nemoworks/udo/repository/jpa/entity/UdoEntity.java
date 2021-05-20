package info.nemoworks.udo.repository.jpa.entity;

import com.github.wnameless.json.flattener.JsonFlattener;
import com.github.wnameless.json.unflattener.JsonUnflattener;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import info.nemoworks.udo.model.Udo;
import info.nemoworks.udo.model.UdoType;
import org.springframework.context.annotation.Lazy;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import java.util.Map;

//import info.nemoworks.udo.model.MetaInfo;

@Entity
public class UdoEntity extends FlattenEntity {

    @Lazy
    @OneToOne(targetEntity = TypeEntity.class)
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
        TypeEntity typeEntity = TypeEntity.from(udo.getType());


        udoEntity.setId(udo.getId());
        udoEntity.setTypeEntity(typeEntity);
        udoEntity.setTuples(JsonFlattener.flattenAsMap(new Gson().toJson(udo.getData())));
//        udoEntity.setMetaInfo(udo.getMetaInfo());
        udoEntity.setCreatedBy(udo.createdBy);
        return udoEntity;

    }

    public Udo toUdo() {
        String unFlattenedData = JsonUnflattener.unflatten(this.getTuples());
        JsonObject data = new Gson().fromJson(unFlattenedData, JsonObject.class);
        UdoType udoType = this.typeEntity.toUdoType();
        udoType.setId(this.typeEntity.getId());
        Udo udo = new Udo(udoType, data);
        udo.setId(id);
        udo.setCreatedBy(getCreatedBy());
        //        udo.setMetaInfo(this.getMetaInfo());
        return udo;
    }
}
