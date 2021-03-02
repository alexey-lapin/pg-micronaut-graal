package pg.micronaut.graal.domain.repository;

import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;
import pg.micronaut.graal.domain.model.Article;

import java.util.UUID;

@Repository
public interface DataArticleRepository extends CrudRepository<Article, UUID> {
}
