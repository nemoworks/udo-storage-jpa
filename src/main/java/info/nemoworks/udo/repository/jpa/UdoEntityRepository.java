package info.nemoworks.udo.repository.jpa;

import info.nemoworks.udo.repository.jpa.entity.TypeEntity;
import info.nemoworks.udo.repository.jpa.entity.UdoEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/*
计划实现一个以Entity为实例的关系型数据库持久化层
 */
@Repository
public interface UdoEntityRepository extends JpaRepository<UdoEntity, String> {

    Optional<UdoEntity> findById(String id);

    Optional<UdoEntity> findByUri(String uri);

    List<UdoEntity> findAllByTypeEntity(TypeEntity schemaEntity);

    List<UdoEntity> findAll();

    @Transactional
    void deleteById(String id);

}
