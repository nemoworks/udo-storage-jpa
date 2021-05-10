package info.nemoworks.udo.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import info.nemoworks.udo.repository.jpa.entity.UdroSchema;

import java.util.List;

public interface SchemaEntityRepository extends JpaRepository<UdroSchema, String> {
    UdroSchema findByTableName(String tableName);

    List<UdroSchema> findAll();

    @Transactional
    void deleteByTableName(String tableName);
}
