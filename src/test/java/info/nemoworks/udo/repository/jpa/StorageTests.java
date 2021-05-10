package info.nemoworks.udo.repository.jpa;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import info.nemoworks.udo.model.Udo;
import info.nemoworks.udo.model.UdoSchema;
import info.nemoworks.udo.repository.jpa.manager.Translate;
import info.nemoworks.udo.repository.jpa.manager.UdroSchemaManager;
import info.nemoworks.udo.storage.UdoNotExistException;
import info.nemoworks.udo.storage.UdoPersistException;
import net.sf.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

//@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
class StorageTests {
//
//    @Autowired(required = true)
//    H2UdoWrapperRepository h2UdoWrapperRepository;

    public String loadFromFile(String path) throws IOException {
        return new String(Files.readAllBytes(Paths.get(path)));
    }

    @Test
    void contextLoads() {
    }

    @Test
    public void translatingTest() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode obj = objectMapper.readTree(this.loadFromFile("src/test/resources/test0.json"));
        Translate translate = new Translate((ObjectNode) obj);
        translate.startTrans();
        System.out.println(translate.getUTuples());
        translate.printTuples();
        translate.startBackTrans();
        System.out.println(translate.getobjectNode());
    }

//    @Test
//    public void udoStorageTest() throws IOException, UdoPersistException, UdoNotExistException {
//        ObjectMapper objectMapper = new ObjectMapper();
//        JsonNode obj = objectMapper.readTree(this.loadFromFile("src/test/resources/light.json"));
//        UdoSchema udoSchema = new UdoSchema("s-01", obj);
//        System.out.println(h2UdoWrapperRepository.saveSchema(udoSchema));
//        System.out.println(h2UdoWrapperRepository.findAllSchemas());
//        System.out.println(h2UdoWrapperRepository.findSchemaById("s-01"));
//        Udo udo = new Udo("d-01", "s-01", obj);
//        System.out.println(h2UdoWrapperRepository.saveUdo(udo));
//        System.out.println(h2UdoWrapperRepository.findUdoById("d-01"));
//        System.out.println(h2UdoWrapperRepository.findUdosBySchema("s-01"));
//        h2UdoWrapperRepository.deleteUdoById("d-01");
//        h2UdoWrapperRepository.deleteSchemaById("s-01");
//    }
}
