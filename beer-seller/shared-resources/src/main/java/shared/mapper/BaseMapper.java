package shared.mapper;

import jakarta.persistence.EntityManager;
import org.mapstruct.Mapping;
import org.mapstruct.ObjectFactory;
import org.mapstruct.TargetType;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class BaseMapper<Entity> {
    @Autowired
    private EntityManager entityManager;

    @Mapping(target = "id", ignore = true)
    public abstract Entity toEntity(final Long id);

    @ObjectFactory
    public Entity createEntity(final Long id, @TargetType final Class<Entity> clazz) {
        final var entity = entityManager.find(clazz, id);
        if (entity == null) {
            throw new IllegalArgumentException();
        }
        return entity;
    }
}
