package info.nemoworks.udo.repository.jpa;

import info.nemoworks.udo.model.Udo;
import info.nemoworks.udo.model.UdoType;
import info.nemoworks.udo.repository.jpa.entity.TypeEntity;
import info.nemoworks.udo.repository.jpa.entity.UdoEntity;
import info.nemoworks.udo.storage.UdoNotExistException;
import info.nemoworks.udo.storage.UdoPersistException;
import info.nemoworks.udo.storage.UdoRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("WrapperService")
public class H2UdoWrapperRepository implements UdoRepository {

    @Autowired
    private UdoEntityRepository udoEntityRepository;

    @Autowired
    private TypeEntityRepository typeEntityRepository;

    @Override
    public Udo saveUdo(Udo udo) throws UdoPersistException {
        UdoEntity entity = UdoEntity.fromUdo(udo);
        if (udoEntityRepository.findById(entity.getId()).isPresent()) {
            throw new UdoPersistException("Udo" + udo.getId() + "already exists.");
        }
        udoEntityRepository.save(entity);
        return udo;
    }

    @Override
    public Udo sync(Udo udo) throws UdoPersistException {
        UdoEntity udoEntity = UdoEntity.fromUdo(udo);
        if (udoEntityRepository.findById(udoEntity.getId()).isPresent()) {
            throw new UdoPersistException("Udo" + udo.getId() + "already exists.");
        }
        udoEntityRepository.save(udoEntity);
        return udo;
    }

    @Override
    public Udo findUdoById(String id) throws UdoNotExistException {
        if (udoEntityRepository.findById(id).isPresent()) {
            return udoEntityRepository.findById(id).get().toUdo();
        } else {
            throw new UdoNotExistException("Udo" + id + "does not exist.");
        }
    }

//    @Override
//    public Udo findUdoByUri(String uri) throws UdoNotExistException {
//        if (udoEntityRepository.findByUri(uri).isPresent()) {
//            return udoEntityRepository.findByUri(uri).get().toUdo();
//        } else {
//            throw new UdoNotExistException("Udo" + uri + "does not exist.");
//        }
//    }

    @Override
    public List<Udo> findUdosByType(UdoType udoType) {
        List<UdoEntity> udoEntities = udoEntityRepository
            .findAllByTypeEntity(TypeEntity.from(udoType));
        List<Udo> udos = new ArrayList<>();
        for (UdoEntity udoEntity : udoEntities) {
            udos.add(udoEntity.toUdo());
        }
        return udos;
    }

    @Override
    public List<Udo> findUdosByTypeId(String udoTypeId) {
        return null;
    }

    @Override
    public void deleteUdoById(String id) throws UdoNotExistException {
        if (!udoEntityRepository.findById(id).isPresent()) {
            throw new UdoNotExistException("Udo" + id + "does not exist.");
        }
        udoEntityRepository.deleteById(id);
    }

    @Override
    public List<UdoType> findAllTypes() {
        List<TypeEntity> types = typeEntityRepository.findAll();
        List<UdoType> udoTypes = new ArrayList<>();
        for (TypeEntity type : types) {
            udoTypes.add(type.toUdoType());
        }
        return udoTypes;
    }

    @Override
    public UdoType findTypeById(String id) throws UdoNotExistException {
        if (!typeEntityRepository.findById(id).isPresent()) {
            throw new UdoNotExistException("Type" + id + "does not exist.");
        }
        return typeEntityRepository.findById(id).get().toUdoType();
    }

    @Override
    public UdoType saveType(UdoType udoType) throws UdoPersistException {
        TypeEntity typeEntity = TypeEntity.from(udoType);
        if (typeEntityRepository.findById(udoType.getId()).isPresent()) {
            throw new UdoPersistException("Type" + udoType.getId() + "already exists.");
        }
        typeEntityRepository.save(typeEntity);
        return udoType;
    }

    @Override
    public void deleteTypeById(String id) throws UdoNotExistException {
        if (typeEntityRepository.findById(id).isPresent()) {
            throw new UdoNotExistException("Type" + id + "already exists.");
        }
        typeEntityRepository.deleteById(id);
    }

    @Override
    public List<Udo> findAllUdos() {
        List<UdoEntity> udoEntities = udoEntityRepository.findAll();
        List<Udo> udos = new ArrayList<>();
        for (UdoEntity udoEntity : udoEntities) {
            udos.add(udoEntity.toUdo());
        }
        return udos;
    }


}
