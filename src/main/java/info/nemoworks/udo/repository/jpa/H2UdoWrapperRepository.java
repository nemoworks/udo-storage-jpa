package info.nemoworks.udo.repository.jpa;

import info.nemoworks.udo.model.UdoSchema;
import info.nemoworks.udo.storage.UdoNotExistException;
import info.nemoworks.udo.storage.UdoPersistException;
import info.nemoworks.udo.model.Udo;
import info.nemoworks.udo.storage.UdoRepository;
import info.nemoworks.udo.repository.jpa.entity.UdoEntity;
import info.nemoworks.udo.repository.jpa.entity.SchemaEntity;

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

    @Override
    public Udo saveUdo(Udo udo) throws UdoPersistException {
        UdoEntity entity = UdoEntity.fromUdo(udo);
        UdoEntity exist = udoEntityRepository.findByUdoId(entity.getUdoId());
        if (exist != null) throw new UdoPersistException("Udo" + udo.getId() + "already exists.");
        udoEntityRepository.save(entity);
        return udo;
    }

    @Override
    public Udo sync(Udo udo) throws UdoPersistException {
        UdoEntity udoEntity = UdoEntity.fromUdo(udo);
        UdoEntity exist = udoEntityRepository.findByUdoId(udoEntity.getUdoId());
        if (exist != null) throw new UdoPersistException("Udo" + udo.getId() + "already exists.");
        udoEntityRepository.save(udoEntity);
        return udo;
    }

    @Override
    public Udo findUdoById(String id) {
        return udoEntityRepository.findByUdoId(id).toUdo();
    }

    @Override
    public List<Udo> findUdosBySchema(UdoSchema udoSchema) {
        List<UdoEntity> udoEntities = udoEntityRepository.findAllBySchemaEntity(SchemaEntity.from(udoSchema));
        List<Udo> udos = new ArrayList<>();
        for (UdoEntity udoEntity : udoEntities) {
            udos.add(udoEntity.toUdo());
        }
        return udos;
    }

    @Override
    public void deleteUdoById(String id) throws UdoNotExistException {
        UdoEntity exist = udoEntityRepository.findByUdoId(id);
        if (exist == null) throw new UdoNotExistException("Udo" + id + "does not exist.");
        udoEntityRepository.deleteByUdoId(id);
    }

    @Override
    public List<UdoSchema> findAllSchemas() {
        List<SchemaEntity> schemas = schemaEntityRepository.findAll();
        List<UdoSchema> udoSchemas = new ArrayList<>();
        for (SchemaEntity schema : schemas) {
            udoSchemas.add(schema.toUdoSchema());
        }
        return udoSchemas;
    }

    @Override
    public UdoSchema findSchemaById(String id) {
        return schemaEntityRepository.findBySchemaId(id).toUdoSchema();
    }

    @Override
    public UdoSchema saveSchema(UdoSchema udoSchema) throws UdoPersistException{
        SchemaEntity schemaEntity = SchemaEntity.from(udoSchema);
        SchemaEntity exist = schemaEntityRepository.findBySchemaId(schemaEntity.getSchemaId());
        if (exist != null) throw new UdoPersistException("Schema" + udoSchema.getId() + "already exists.");
        schemaEntityRepository.save(schemaEntity);
        return udoSchema;
    }

    @Override
    public void deleteSchemaById(String id) throws UdoNotExistException {
        SchemaEntity exist = schemaEntityRepository.findBySchemaId(id);
        if (exist == null) throw new UdoNotExistException("Schema" + id + "does not exist.");
        schemaEntityRepository.deleteBySchemaId(id);
    }


}
