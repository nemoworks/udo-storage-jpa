package info.nemoworks.udo.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import info.nemoworks.udo.repository.jpa.entity.SchemaEntity;

import java.util.List;

@Component
public interface SchemaEntityRepository extends JpaRepository<SchemaEntity, String> {
    SchemaEntity findBySchemaId(String schemaId);

    List<SchemaEntity> findAll();

    @Transactional
    void deleteBySchemaId(String schemaId);
}
