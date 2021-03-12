package pg.micronaut.graal.domain.repository;

import io.micronaut.context.ApplicationContext;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.core.annotation.TypeHint;
import io.micronaut.core.beans.BeanWrapper;
import io.micronaut.core.reflect.ReflectionUtils;
import io.micronaut.inject.BeanDefinition;
import io.micronaut.inject.ExecutableMethod;
import lombok.RequiredArgsConstructor;
import pg.micronaut.graal.domain.model.User;

import javax.inject.Singleton;
import java.util.Optional;
import java.util.UUID;

@TypeHint(
        value = {JpaUserRepositoryAdapter.class},
        accessType = {
                TypeHint.AccessType.ALL_DECLARED_CONSTRUCTORS,
                TypeHint.AccessType.ALL_DECLARED_FIELDS,
                TypeHint.AccessType.ALL_PUBLIC_METHODS
        }
)
@Introspected
@RequiredArgsConstructor
@Singleton
public class JpaUserRepositoryAdapter implements UserRepository {

    private final DataUserRepository repository;
    private final ApplicationContext context;

    @Override
    public Optional<User> findByEmail(String email) {
        return repository.findByEmail(email);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return repository.findByUsername(username);
    }

    @Override
    public User save(User user) {
        UUID id = user.getId();
        System.out.println(">> " + id.getClass().getName());
        BeanDefinition<? extends DataUserRepository> beanDefinition = context.getBeanDefinition(repository.getClass());
        Optional<? extends ExecutableMethod<? extends DataUserRepository, Object>> existsById = beanDefinition.findMethod("existsById", UUID.class);
        System.out.println(">> " + existsById.get());
        boolean b = repository.existsById(id);
        if (b) {
            return repository.update(user);
        }
        return repository.save(user);
    }

}

