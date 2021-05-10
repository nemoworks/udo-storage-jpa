package info.nemoworks.udo.repository.jpa.manager;

import com.fasterxml.jackson.databind.node.ObjectNode;
import info.nemoworks.udo.model.Udo;
import info.nemoworks.udo.repository.jpa.UdroDocumentRepository;
import info.nemoworks.udo.repository.jpa.entity.UdroDocument;
import info.nemoworks.udo.storage.UdoNotExistException;
import info.nemoworks.udo.storage.UdoPersistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UdroDocumentManager {
    @Autowired
    private UdroDocumentRepository udroDocumentRepository;

    public UdroDocumentManager(UdroDocumentRepository udroDocumentRepository) {
        this.udroDocumentRepository = udroDocumentRepository;
    }

    public List<UdroDocument> findAll() {
        return udroDocumentRepository.findAll();
    }

    public UdroDocument findByName(String name) {
        UdroDocument UDRODocument = udroDocumentRepository.findByTableName(name);
//        if (UDRODocument == null) {
//            throw new UdroPersistException("UDRO " + name + " does not exist.");
//        }
        return UDRODocument;
    }

    public UdroDocument findByFirstName(String firstName) {
        return udroDocumentRepository.findByFirstTableName(firstName);
    }

    public List<UdroDocument> findAllBySecondName(String secondName) {
        return udroDocumentRepository.findAllBySecondTableName(secondName);
    }

    public void deleteByFirstName(String firstName) throws UdoNotExistException {
        UdroDocument UDRODocument = udroDocumentRepository.findByFirstTableName(firstName);
        if (UDRODocument == null) {
            throw new UdoNotExistException("UDRO " + firstName + " does not exist.");
        }
        udroDocumentRepository.deleteByFirstTableName(firstName);
    }

    public void deleteByName(String name) throws UdoNotExistException{
        UdroDocument UDRODocument = udroDocumentRepository.findByTableName(name);
        if (UDRODocument == null) {
            throw new UdoNotExistException("UDRO " + name + " does not exist.");
        }
        udroDocumentRepository.deleteByTableName(name);
    }

    public UdroDocument saveUdo(Udo udo) {
        // JSONObject obj = udo.getContent();
        String firstTableName = udo.getId();
        String secondTableName = udo.getSchemaId();
        Translate translate = new Translate((ObjectNode) udo.getData());
        translate.startTrans();
        UdroDocument udroDocument = new UdroDocument(translate.getUTuples(), firstTableName, secondTableName);
//        if (udroDocumentRepository.findByTableName(udroDocument.getTableName()) != null) {
//            throw new UdroPersistException("UDRO " + udroDocument.getTableName() + " already exists.");
//        }
        System.out.println("save UDRO: " + udroDocument);
        return udroDocumentRepository.save(udroDocument);
    }

    public UdroDocument updateUdo(Udo udo) throws UdoPersistException {
        // JSONObject obj = udo.getContent();
        String firstTableName = udo.getId();
        String secondTableName = udo.getSchemaId();
        Translate translate = new Translate((ObjectNode) udo.getData());
        translate.startTrans();
        UdroDocument udroDocument = new UdroDocument(translate.getUTuples(), firstTableName, secondTableName);
        if (udroDocumentRepository.findByTableName(udroDocument.getTableName()) == null) {
            throw new UdoPersistException("UDRO " + udroDocument.getTableName() + " does not exist.");
        }
        udroDocumentRepository.deleteByTableName(firstTableName + "_" + secondTableName);
        return udroDocumentRepository.save(udroDocument);
    }
}
