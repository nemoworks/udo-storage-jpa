package info.nemoworks.udo.repository.manager;

import info.nemoworks.udo.model.Udo;
import info.nemoworks.udo.repository.UdroDocumentRepository;
import info.nemoworks.udo.repository.exception.UdroPersistException;
import info.nemoworks.udo.repository.model.UdroDocument;
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

    public UdroDocument findByName(String name) throws UdroPersistException {
        UdroDocument UDRODocument = udroDocumentRepository.findByTableName(name);
        if (UDRODocument == null) {
            throw new UdroPersistException("UDRO " + name + " does not exist.");
        }
        return UDRODocument;
    }

    public void deleteByName(String name) throws UdroPersistException {
        UdroDocument UDRODocument = udroDocumentRepository.findByTableName(name);
        if (UDRODocument == null) {
            throw new UdroPersistException("UDRO " + name + " does not exist.");
        }
        udroDocumentRepository.deleteByTableName(name);
    }

    public UdroDocument saveUdo(Udo udo) throws UdroPersistException {
        // JSONObject obj = udo.getContent();
        String firstTableName = udo.getUdoi();
        String secondTableName = udo.getSchemaId();
        Translate translate = new Translate(udo.getContent());
        translate.startTrans();
        UdroDocument udroDocument = new UdroDocument(translate.getUTuples(), firstTableName, secondTableName);
        if (udroDocumentRepository.findByTableName(udroDocument.getTableName()) != null) {
            throw new UdroPersistException("UDRO " + udroDocument.getTableName() + " already exists.");
        }
        System.out.println("save UDRO: " + udroDocument);
        return udroDocumentRepository.save(udroDocument);
    }

    public UdroDocument updateUdo(Udo udo) throws UdroPersistException {
        // JSONObject obj = udo.getContent();
        String firstTableName = udo.getUdoi();
        String secondTableName = udo.getSchemaId();
        Translate translate = new Translate(udo.getContent());
        translate.startTrans();
        UdroDocument udroDocument = new UdroDocument(translate.getUTuples(), firstTableName, secondTableName);
        if (udroDocumentRepository.findByTableName(udroDocument.getTableName()) == null) {
            throw new UdroPersistException("UDRO " + udroDocument.getTableName() + " does not exist.");
        }
        udroDocumentRepository.deleteByTableName(firstTableName + "_" + secondTableName);
        return udroDocumentRepository.save(udroDocument);
    }
}
