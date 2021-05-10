package info.nemoworks.udo.repository.jpa;

import info.nemoworks.udo.model.UdoSchema;
import info.nemoworks.udo.storage.UdoNotExistException;
import info.nemoworks.udo.storage.UdoPersistException;
import info.nemoworks.udo.model.Udo;
import info.nemoworks.udo.storage.UdoRepository;
import info.nemoworks.udo.repository.jpa.entity.UTuple;
import info.nemoworks.udo.repository.jpa.entity.UdoEntity;
import info.nemoworks.udo.repository.jpa.entity.UdroDocument;
import info.nemoworks.udo.repository.jpa.entity.UdroSchema;
import info.nemoworks.udo.repository.jpa.manager.Translate;
import info.nemoworks.udo.repository.jpa.manager.UdroDocumentManager;
import info.nemoworks.udo.repository.jpa.manager.UdroSchemaManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class H2UdoWrapperRepository implements UdoRepository {
    @Autowired
    private UdroDocumentManager udroDocumentManager;

    @Autowired
    private UdroSchemaManager udroSchemaManager;

    
    @Autowired
    private UdoEntityRepository udoEntityRepository;

    private Udo fromUdro2Udo(UdroDocument udroDocument) {
        List<UTuple> uTuples = udroDocument.getUTuples();
        Translate translate = new Translate(uTuples);
        translate.startBackTrans();
        return new Udo(udroDocument.getFirstTableName(), udroDocument.getSecondTableName(), translate.getobjectNode());
    }

    private UdoSchema fromUdro2Udo(UdroSchema udroSchema) {
        List<UTuple> uTuples = udroSchema.getUTuples();
//        System.out.println("translating tuples: ");
//        for(UTuple uTuple: udroSchema.getUTuples()) uTuple.printTuple();
        Translate translate = new Translate(uTuples);
        translate.startBackTrans();
//        System.out.println("2Udo: " + translate.getJsonObject().toString());
        return new UdoSchema(udroSchema.getTableName(), translate.getobjectNode());
    }

    @Override
    public Udo saveUdo(Udo udo) throws UdoPersistException {

        UdoEntity entity = UdoEntity.fromUdo(udo);
        udoEntityRepository.save(entity);

        UdroDocument table = udroDocumentManager.saveUdo(udo);
//        List<UTuple> uTuples = table.getUTuples();
//        Translate translate = new Translate(uTuples);
//        translate.startBackTrans();
        String firstTableName = udo.getId();
        String secondTableName = udo.getSchemaId();
        UdroDocument udroDocument = udroDocumentManager.findByName(firstTableName + "_" + secondTableName);
//        String jStr = JSON.toJSONString(translate.getJsonObject());
//        return JSONObject.parseObject(jStr, udo.getClass());
//        System.out.println("uTable got: " + udro);
        return this.fromUdro2Udo(udroDocument);
    }

    @Override
    public Udo sync(Udo udo) throws UdoPersistException {
        UdroDocument UDRODocument = udroDocumentManager.updateUdo(udo);
        return this.fromUdro2Udo(UDRODocument);
    }

    @Override
    public Udo findUdoById(String id) {
        return this.fromUdro2Udo(udroDocumentManager.findByFirstName(id));
    }

    @Override
    public List<Udo> findUdosBySchema(String schemaId) {
        List<UdroDocument> udroDocuments = udroDocumentManager.findAllBySecondName(schemaId);
        List<Udo> udos = new ArrayList<>();
        for (UdroDocument udroDocument : udroDocuments) {
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
        List<UdroSchema> schemas = udroSchemaManager.findAll();
        List<UdoSchema> udoSchemas = new ArrayList<>();
        for (UdroSchema udroSchema : schemas) {
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
        UdroSchema sav = udroSchemaManager.saveUdoSchema(udoSchema);
        String tableName = udoSchema.getId();
        UdroSchema udroSchema = udroSchemaManager.findByName(tableName);
//        System.out.println("find udroschema: ");
//        for (UTuple uTuple: udroSchema.getUTuples()) uTuple.printTuple();
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
