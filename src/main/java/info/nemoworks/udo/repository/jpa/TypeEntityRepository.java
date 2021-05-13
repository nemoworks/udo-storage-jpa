package info.nemoworks.udo.repository.jpa;

import info.nemoworks.udo.repository.jpa.entity.TypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Component
public interface TypeEntityRepository extends JpaRepository<TypeEntity, String> {
    Optional<TypeEntity> findById(String schemaId);

    List<TypeEntity> findAll();

    @Transactional
    void deleteBySchemaId(String schemaId);
}
