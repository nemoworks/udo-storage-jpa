package info.nemoworks.udo.repository.manager;

import info.nemoworks.udo.model.UdoSchema;
import info.nemoworks.udo.repository.UdroSchemaRepository;
import info.nemoworks.udo.repository.exception.UdroSchemaPersistException;
import info.nemoworks.udo.repository.model.UdroSchema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UdroSchemaManager {
    @Autowired
    UdroSchemaRepository UDROSchemaRepository;

    public UdroSchemaManager(UdroSchemaRepository UDROSchemaRepository) {
        this.UDROSchemaRepository = UDROSchemaRepository;
    }

    public List<UdroSchema> findAll() {
        return UDROSchemaRepository.findAll();
    }

    public UdroSchema findByName(String name) throws UdroSchemaPersistException {
        UdroSchema UDROSchema = UDROSchemaRepository.findByTableName(name);
        if (UDROSchema == null) {
            throw new UdroSchemaPersistException("UDROSchema " + name + " does not exist.");
        }
        return UDROSchema;
    }

    public void deleteByName(String name) throws UdroSchemaPersistException {
        UdroSchema UDROSchema = UDROSchemaRepository.findByTableName(name);
        if (UDROSchema == null) {
            throw new UdroSchemaPersistException("UDROSchema " + name + " does not exist.");
        }
        UDROSchemaRepository.deleteByTableName(name);
    }

    public UdroSchema saveUdoSchema(UdoSchema udoSchema) throws UdroSchemaPersistException {
        // JSONObject obj = udo.getContent();
        String tableName = udoSchema.getUdoi();
        Translate translate = new Translate(udoSchema.getSchemaContent());
        translate.startTrans();
        UdroSchema UDROSchema = new UdroSchema(tableName, translate.getUTuples());
        if (UDROSchemaRepository.findByTableName(UDROSchema.getTableName()) != null) {
            throw new UdroSchemaPersistException("UDROSchema: " + UDROSchema.getTableName() + " already exists.");
        }
        System.out.println("save UDROSchema: " + UDROSchema.getTableName());
//        for (UTuple uTuple: UDROSchema.getUTuples()) uTuple.printTuple();
        return UDROSchemaRepository.save(UDROSchema);
    }

    public UdroSchema updateUdoSchema(UdoSchema udoSchema) throws UdroSchemaPersistException {
        // JSONObject obj = udo.getContent();
        String tableName = udoSchema.getUdoi();
        Translate translate = new Translate(udoSchema.getSchemaContent());
        translate.startTrans();
        UdroSchema UDROSchema = new UdroSchema(tableName, translate.getUTuples());
        if (UDROSchemaRepository.findByTableName(UDROSchema.getTableName()) == null) {
            throw new UdroSchemaPersistException("UDROSchema " + UDROSchema.getTableName() + " does not exist.");
        }
        UDROSchemaRepository.deleteByTableName(tableName);
        return UDROSchemaRepository.save(UDROSchema);
    }
}
