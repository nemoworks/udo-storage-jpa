package info.nemoworks.udo.repository.jpa;

import info.nemoworks.udo.model.UdoSchema;
import info.nemoworks.udo.storage.UdoNotExistException;
import info.nemoworks.udo.storage.UdoPersistException;
import info.nemoworks.udo.model.Udo;
import info.nemoworks.udo.storage.UdoRepository;
import info.nemoworks.udo.repository.jpa.entity.TupleEntity;
import info.nemoworks.udo.repository.jpa.entity.UdoEntity;
import info.nemoworks.udo.repository.jpa.entity.SchemaEntity;
import info.nemoworks.udo.repository.jpa.manager.Translate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class H2UdoWrapperRepository implements UdoRepository {
    
    @Autowired
    private UdoEntityRepository udoEntityRepository;

    @Autowired
    private SchemaEntityRepository schemaEntityRepository;

    private Udo fromUdro2Udo(UdoEntity udroDocument) {
        List<TupleEntity> uTuples = udroDocument.getTupleEntitys();
        Translate translate = new Translate(uTuples);
        translate.startBackTrans();
        return new Udo(udroDocument.getFirstTableName(), udroDocument.getSecondTableName(), translate.getobjectNode());
    }

    private UdoSchema fromUdro2Udo(SchemaEntity udroSchema) {
        List<TupleEntity> uTuples = udroSchema.getTupleEntitys();
//        System.out.println("translating tuples: ");
//        for(TupleEntity uTuple: udroSchema.getTupleEntitys()) uTuple.printTuple();
        Translate translate = new Translate(uTuples);
        translate.startBackTrans();
//        System.out.println("2Udo: " + translate.getJsonObject().toString());
        return new UdoSchema(udroSchema.getTableName(), translate.getobjectNode());
    }

    @Override
    public Udo saveUdo(Udo udo) throws UdoPersistException {

        UdoEntity entity = UdoEntity.fromUdo(udo);
        assert entity != null;
        udoEntityRepository.save(entity);

//        UdoEntity table = udroDocumentManager.saveUdo(udo);
//        List<TupleEntity> uTuples = table.getTupleEntitys();
//        Translate translate = new Translate(uTuples);
//        translate.startBackTrans();
        String firstTableName = udo.getId();
        String secondTableName = udo.getSchemaId();
        UdoEntity udoEntity = udoEntityRepository.findByUdoId(udo.getId());
//        String jStr = JSON.toJSONString(translate.getJsonObject());
//        return JSONObject.parseObject(jStr, udo.getClass());
//        System.out.println("uTable got: " + udro);
        return this.fromUdro2Udo(udoEntity);
    }

    @Override
    public Udo sync(Udo udo) throws UdoPersistException {
        UdoEntity UDRODocument = udroDocumentManager.updateUdo(udo);
        return this.fromUdro2Udo(UDRODocument);
    }

    @Override
    public Udo findUdoById(String id) {
        return this.fromUdro2Udo(udroDocumentManager.findByFirstName(id));
    }

    @Override
    public List<Udo> findUdosBySchema(String schemaId) {
        List<UdoEntity> udroDocuments = udroDocumentManager.findAllBySecondName(schemaId);
        List<Udo> udos = new ArrayList<>();
        for (UdoEntity udroDocument : udroDocuments) {
            udos.add(this.fromUdro2Udo(udroDocument));
        }
        return udos;
    }

    @Override
    public void deleteUdoById(String id) throws UdoNotExistException {
        udroDocumentManager.deleteByFirstName(id);
    }

    @Override
    public List<UdoSchema> findAllSchemas() {
        List<SchemaEntity> schemas = udroSchemaManager.findAll();
        List<UdoSchema> udoSchemas = new ArrayList<>();
        for (SchemaEntity udroSchema : schemas) {
            udoSchemas.add(this.fromUdro2Udo(udroSchema));
        }
        return udoSchemas;
    }

    @Override
    public UdoSchema findSchemaById(String id) {
        return this.fromUdro2Udo(udroSchemaManager.findByName(id));
    }

    @Override
    public UdoSchema saveSchema(UdoSchema udoSchema) throws UdoPersistException{
        SchemaEntity sav = udroSchemaManager.saveUdoSchema(udoSchema);
        String tableName = udoSchema.getId();
        SchemaEntity udroSchema = udroSchemaManager.findByName(tableName);
//        System.out.println("find udroschema: ");
//        for (TupleEntity uTuple: udroSchema.getTupleEntitys()) uTuple.printTuple();
        return fromUdro2Udo(udroSchema);
    }

    @Override
    public void deleteSchemaById(String id) throws UdoNotExistException {
        udroSchemaManager.deleteByName(id);
    }

    @Override
    public List<Udo> findUdosBySchema(UdoSchema schema) {
        // TODO Auto-generated method stub
        return null;
    }

}
