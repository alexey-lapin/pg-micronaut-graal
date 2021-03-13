package pg.micronaut.graal.domain.repository;

import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;
import pg.micronaut.graal.domain.model.Dummy2;

@Repository
public interface DataDummy2Repository extends CrudRepository<Dummy2, String> {
}
