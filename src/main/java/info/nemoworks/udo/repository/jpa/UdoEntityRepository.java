package info.nemoworks.udo.repository.jpa;

import info.nemoworks.udo.repository.jpa.entity.TypeEntity;
import info.nemoworks.udo.repository.jpa.entity.UdoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/*
计划实现一个以Entity为实例的关系型数据库持久化层
 */
@Component
public interface UdoEntityRepository extends JpaRepository<UdoEntity, String> {
    Optional<UdoEntity> findById(String udoId);

    List<UdoEntity> findAllByTypeEntity(TypeEntity schemaEntity);

    List<UdoEntity> findAll();

    @Transactional
    void deleteByUdoId(String udoId);

}
