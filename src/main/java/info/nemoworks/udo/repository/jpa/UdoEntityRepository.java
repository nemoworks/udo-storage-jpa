package info.nemoworks.udo.repository.jpa;

import info.nemoworks.udo.repository.jpa.entity.SchemaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import info.nemoworks.udo.repository.jpa.entity.UdoEntity;

import java.util.List;

/*
计划实现一个以Entity为实例的关系型数据库持久化层
 */
@Component
public interface UdoEntityRepository extends JpaRepository<UdoEntity, String> {
    UdoEntity findByUdoId(String udoId);
    List<UdoEntity> findAllBySchemaEntity(SchemaEntity schemaEntity);
    List<UdoEntity> findAll();

    @Transactional
    void deleteByUdoId(String udoId);
    
}
