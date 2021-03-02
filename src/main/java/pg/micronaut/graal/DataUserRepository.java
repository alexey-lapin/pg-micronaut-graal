package pg.micronaut.graal;

import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface DataUserRepository extends CrudRepository<User, UUID> {

    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(@Nullable String username);

}
