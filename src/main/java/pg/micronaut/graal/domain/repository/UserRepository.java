package pg.micronaut.graal.domain.repository;

import pg.micronaut.graal.domain.model.User;

import java.util.Optional;

public interface UserRepository {

    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String username);

    User save(User user);

}
