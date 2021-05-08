package info.nemoworks.udo.repository;

import info.nemoworks.udo.repository.model.UdroDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/*
计划实现一个以Entity为实例的关系型数据库持久化层
 */
@Component
public interface UdroDocumentRepository extends JpaRepository<UdroDocument, String> {
    UdroDocument findByTableName(String tableName);

    List<UdroDocument> findAll();

    @Transactional
    void deleteByTableName(String tableName);
}
