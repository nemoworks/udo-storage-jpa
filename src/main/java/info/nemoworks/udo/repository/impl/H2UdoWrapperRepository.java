package info.nemoworks.udo.repository.impl;

import info.nemoworks.udo.exception.UdoPersistException;
import info.nemoworks.udo.model.Udo;
import info.nemoworks.udo.repository.UdoRepository;
import info.nemoworks.udo.repository.exception.UdroPersistException;
import info.nemoworks.udo.repository.manager.Translate;
import info.nemoworks.udo.repository.manager.UdroDocumentManager;
import info.nemoworks.udo.repository.manager.UdroSchemaManager;
import info.nemoworks.udo.repository.model.UTuple;
import info.nemoworks.udo.repository.model.UdroDocument;
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

    private Udo fromUdro2Udo(UdroDocument udroDocument) {
        List<UTuple> uTuples = udroDocument.getUTuples();
        Translate translate = new Translate(uTuples);
        translate.startBackTrans();
        return new Udo(udroDocument.getFirstTableName(), udroDocument.getSecondTableName(), translate.getJsonObject());
    }

    private UdoSchema fromUdro2Udo(UdroSchema udroSchema) {
        List<UTuple> uTuples = udroSchema.getUTuples();
//        System.out.println("translating tuples: ");
//        for(UTuple uTuple: udroSchema.getUTuples()) uTuple.printTuple();
        Translate translate = new Translate(uTuples);
        translate.startBackTrans();
//        System.out.println("2Udo: " + translate.getJsonObject().toString());
        return new UdoSchema(udroSchema.getTableName(), translate.getJsonObject());
    }

    @Override
    public Udo saveUdo(Udo udo, String schemaId) throws UdoPersistException, UdroPersistException {
        UdroDocument table = udroDocumentManager.saveUdo(udo);
//        List<UTuple> uTuples = table.getUTuples();
//        Translate translate = new Translate(uTuples);
//        translate.startBackTrans();
        String firstTableName = udo.getUdoi();
        String secondTableName = udo.getSchemaId();
        UdroDocument udroDocument = udroDocumentManager.findByName(firstTableName + "_" + secondTableName);
//        String jStr = JSON.toJSONString(translate.getJsonObject());
//        return JSONObject.parseObject(jStr, udo.getClass());
//        System.out.println("uTable got: " + udro);
        return this.fromUdro2Udo(udroDocument);
    }

    @Override
    public Udo findUdo(String udoi, String schemaId) throws UdroPersistException {
        return this.fromUdro2Udo(udroDocumentManager.findByName(udoi + "_" + schemaId));
    }

    @Override
    public List<Udo> findAllUdos(String schemaId) {
        List<UdroDocument> udroDocuments = udroDocumentManager.findAll();
        List<Udo> udos = new ArrayList<>();
        for (UdroDocument udroDocument : udroDocuments) {
            udos.add(this.fromUdro2Udo(udroDocument));
        }
        return udos;
    }

    @Override
    public void deleteUdo(String udoi, String schemaId) throws UdroPersistException {
        udroDocumentManager.deleteByName(udoi + "_" + schemaId);
    }

    @Override
    public Udo updateUdo(Udo udo, String udoi, String schemaId) throws UdroPersistException {
        UdroDocument UDRODocument = udroDocumentManager.updateUdo(udo);
        return this.fromUdro2Udo(UDRODocument);
//        return null;
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
    public UdoSchema findSchemaById(String udoi) throws UdroSchemaPersistException {
        return this.fromUdro2Udo(udroSchemaManager.findByName(udoi));
    }

    @Override
    public UdoSchema saveSchema(UdoSchema udoSchema) throws UdroSchemaPersistException {
        UdroSchema sav = udroSchemaManager.saveUdoSchema(udoSchema);
        String tableName = udoSchema.getUdoi();
        UdroSchema udroSchema = udroSchemaManager.findByName(tableName);
//        System.out.println("find udroschema: ");
//        for (UTuple uTuple: udroSchema.getUTuples()) uTuple.printTuple();
        return fromUdro2Udo(udroSchema);
    }

    @Override
    public void deleteSchemaById(String udoi) throws UdroSchemaPersistException {
        udroSchemaManager.deleteByName(udoi);
    }

    @Override
    public UdoSchema updateSchema(UdoSchema udoSchema, String udoi) throws UdroSchemaPersistException {
        UdroSchema udroSchema = udroSchemaManager.updateUdoSchema(udoSchema);
        return this.fromUdro2Udo(udroSchema);
    }
}
