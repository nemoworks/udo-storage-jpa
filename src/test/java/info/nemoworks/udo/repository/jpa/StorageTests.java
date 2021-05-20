package info.nemoworks.udo.repository.jpa;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import info.nemoworks.udo.model.Udo;
import info.nemoworks.udo.model.UdoType;
import info.nemoworks.udo.repository.jpa.entity.TypeEntity;
import info.nemoworks.udo.repository.jpa.entity.UdoEntity;
import info.nemoworks.udo.storage.UdoNotExistException;
import info.nemoworks.udo.storage.UdoPersistException;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

//import info.nemoworks.udo.model.MetaInfo;

//@SpringBootTest(classes = {info.nemoworks.udo.repository.jpa.TypeEntityRepository.class
//        , info.nemoworks.udo.repository.jpa.UdoEntityRepository.class
//        , info.nemoworks.udo.repository.jpa.H2UdoWrapperRepository.class})
@RunWith(SpringRunner.class)
//@ComponentScan(basePackageClasses = {TypeEntityRepository.class, UdoEntityRepository.class, H2UdoWrapperRepository.class})
//@EnableJpaRepositories(value = "info.nemoworks.udo.repository.jpa")
//@EntityScan
@DataJpaTest
//@SpringBootConfiguration
//@Import(TypeEntityRepository.class)
class StorageTests {
//    @Autowired
//    @Qualifier("WrapperService")
//    private H2UdoWrapperRepository h2UdoWrapperRepository;

    @Autowired
    private UdoEntityRepository udoEntityRepository;

    @Autowired
    private TypeEntityRepository typeEntityRepository;

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

    @Test
    public void udoStorageTest() throws IOException, UdoNotExistException, UdoPersistException {
        JsonObject obj = new Gson().fromJson(this.loadFromFile("src/test/resources/light.json"), JsonObject.class);
        UdoType udoType = new UdoType(obj);
//        MetaInfo metaInfo = new MetaInfo() {
//            {
//                this.createdBy = "nemoworks";
//            }
//        };
        udoType.setId("s-01");
//        udoType.setMetaInfo(metaInfo);
        udoType.setCreatedBy("nemoworks");
        typeEntityRepository.save(TypeEntity.from(udoType)).printTuples();
        System.out.println(typeEntityRepository.findById("s-01").isPresent());

//        System.out.println(typeEntityRepository.findAll());
//        System.out.println(h2UdoWrapperRepository.saveType(udoType));
//        System.out.println(h2UdoWrapperRepository.findAllTypes());
//        System.out.println(h2UdoWrapperRepository.findTypeById("s-01"));
        Udo udo = new Udo(udoType, obj);
        udo.setId("d-01");
//        udo.setMetaInfo(metaInfo);
        udo.setCreatedBy("nemoworks");
        udoEntityRepository.save(UdoEntity.fromUdo(udo)).printTuples();
//        System.out.println(udoEntityRepository.save(UdoEntity.fromUdo(udo)));
        System.out.println(udoEntityRepository.findById("d-01").isPresent());
//        System.out.println(udoEntityRepository.findAll());
//        System.out.println(h2UdoWrapperRepository.saveUdo(udo));
//        System.out.println(h2UdoWrapperRepository.findUdoById("d-01"));
//        System.out.println(h2UdoWrapperRepository.findUdosByType(udoType));
//        h2UdoWrapperRepository.deleteUdoById("d-01");
//        h2UdoWrapperRepository.deleteTypeById("s-01");
    }
}
