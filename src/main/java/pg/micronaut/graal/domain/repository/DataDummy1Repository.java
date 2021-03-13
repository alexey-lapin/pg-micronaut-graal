package pg.micronaut.graal.domain.repository;

import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;
import pg.micronaut.graal.domain.model.Dummy1;

import java.time.LocalDate;

@Repository
public interface DataDummy1Repository extends CrudRepository<Dummy1, LocalDate> {
}
