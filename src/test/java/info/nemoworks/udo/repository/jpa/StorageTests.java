package info.nemoworks.udo.repository.jpa;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import info.nemoworks.udo.model.UdoType;
import info.nemoworks.udo.repository.jpa.entity.TypeEntity;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

//@SpringBootTest
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
        JsonObject testObj = new Gson().fromJson(this.loadFromFile("src/test/resources/test0.json"), JsonObject.class);
        UdoType udoType = new UdoType(testObj);
        udoType.setId("t-01");
        TypeEntity typeEntity = TypeEntity.from(udoType);
        typeEntity.printTuples();
        UdoType test = typeEntity.toUdoType();
        System.out.println(new Gson().toJson(test.getSchema()));
    }

//    @Test
//    public void udoStorageTest() throws IOException, UdoPersistException, UdoNotExistException {
//        JsonObject obj = new Gson().fromJson(this.loadFromFile("src/test/resources/light.json"), JsonObject.class);
//        UdoType udoType = new UdoType(obj);
//        udoType.setId("s-01");
//        System.out.println(h2UdoWrapperRepository.saveType(udoType));
//        System.out.println(h2UdoWrapperRepository.findAllTypes());
//        System.out.println(h2UdoWrapperRepository.findTypeById("s-01"));
//        Udo udo = new Udo(udoType, obj);
//        udo.setId("d-01");
//        System.out.println(h2UdoWrapperRepository.saveUdo(udo));
//        System.out.println(h2UdoWrapperRepository.findUdoById("d-01"));
//        System.out.println(h2UdoWrapperRepository.findUdosByType(udoType));
//        h2UdoWrapperRepository.deleteUdoById("d-01");
//        h2UdoWrapperRepository.deleteTypeById("s-01");
//    }
}
