package info.nemoworks.udo.repository.jpa;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import info.nemoworks.udo.model.Udo;
import info.nemoworks.udo.model.UdoSchema;
import info.nemoworks.udo.repository.jpa.entity.Translate;
import info.nemoworks.udo.storage.UdoNotExistException;
import info.nemoworks.udo.storage.UdoPersistException;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

//@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
class StorageTests {

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
        System.out.println(translate.getTupleEntitys());
        translate.printTuples();
        translate.startBackTrans();
        System.out.println(translate.getObjectNode());
    }

//    @Test
//    public void udoStorageTest() throws IOException, UdoPersistException, UdoNotExistException {
//        ObjectMapper objectMapper = new ObjectMapper();
//        JsonNode obj = objectMapper.readTree(this.loadFromFile("src/test/resources/light.json"));
//        UdoSchema udoSchema = new UdoSchema(obj);
//        udoSchema.setId("s-01");
//        System.out.println(h2UdoWrapperRepository.saveSchema(udoSchema));
//        System.out.println(h2UdoWrapperRepository.findAllSchemas());
//        System.out.println(h2UdoWrapperRepository.findSchemaById("s-01"));
//        Udo udo = new Udo(udoSchema, obj);
//        udo.setId("d-01");
//        System.out.println(h2UdoWrapperRepository.saveUdo(udo));
//        System.out.println(h2UdoWrapperRepository.findUdoById("d-01"));
//        System.out.println(h2UdoWrapperRepository.findUdosBySchema(udoSchema));
//        h2UdoWrapperRepository.deleteUdoById("d-01");
//        h2UdoWrapperRepository.deleteSchemaById("s-01");
//    }
}
