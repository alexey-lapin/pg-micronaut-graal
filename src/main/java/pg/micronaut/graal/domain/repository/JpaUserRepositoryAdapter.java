package pg.micronaut.graal.domain.repository;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.core.annotation.TypeHint;
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
        boolean b = repository.existsById(id);
        if (b) {
            return repository.update(user);
        }
        return repository.save(user);
    }

}

