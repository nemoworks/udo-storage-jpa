package info.nemoworks.udo.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import info.nemoworks.udo.repository.jpa.entity.UdoEntity;
import info.nemoworks.udo.repository.jpa.entity.UdroDocument;

import java.util.List;

/*
计划实现一个以Entity为实例的关系型数据库持久化层
 */
@Component
public interface UdoEntityRepository extends JpaRepository<UdoEntity, Long> {
    // UdroDocument findByTableName(String tableName);
    // UdroDocument findByFirstTableName(String firstTableName);
    // List<UdroDocument> findAllBySecondTableName(String secondName);
    // List<UdroDocument> findAll();

    // @Transactional
    // void Name(String tableName);

    // @Transactional
    // void deleteByFirstTableName(String firstName);
    public void deleteByUdoId(Long udoid);
    
}
