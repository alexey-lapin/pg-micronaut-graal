package pg.micronaut.graal.domain.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pg.micronaut.graal.domain.model.User;

import javax.inject.Singleton;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Singleton
@Slf4j
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

