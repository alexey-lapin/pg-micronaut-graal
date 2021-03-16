package pg.micronaut.graal.domain.repository;

import io.micronaut.core.annotation.Nullable;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;
import pg.micronaut.graal.domain.model.User;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface DataUserRepository extends CrudRepository<User, UUID> {

    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(@Nullable String username);

}
