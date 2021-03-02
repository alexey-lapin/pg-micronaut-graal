package pg.micronaut.graal.domain.repository;

import lombok.RequiredArgsConstructor;
import pg.micronaut.graal.domain.model.User;

import javax.inject.Singleton;
import java.util.Optional;

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
        if (repository.existsById(user.getId())) {
            return repository.update(user);
        }
        return repository.save(user);
    }

}

