package pg.micronaut.graal.domain.repository;

import edu.umd.cs.findbugs.annotations.NonNull;
import edu.umd.cs.findbugs.annotations.Nullable;
import io.micronaut.context.annotation.Replaces;
import io.micronaut.data.hibernate.operations.HibernateJpaOperations;
import io.micronaut.data.jpa.operations.JpaRepositoryOperations;
import io.micronaut.data.model.runtime.PreparedQuery;
import io.micronaut.transaction.TransactionOperations;
import org.hibernate.SessionFactory;

import java.sql.Connection;
import java.util.concurrent.ExecutorService;

@Replaces(JpaRepositoryOperations.class)
public class HibernateJpaOperationsWrapper extends HibernateJpaOperations {

    public HibernateJpaOperationsWrapper(@NonNull SessionFactory sessionFactory, @NonNull TransactionOperations<Connection> transactionOperations, @Nullable ExecutorService executorService) {
        super(sessionFactory, transactionOperations, executorService);
    }

    @Nullable
    @Override
    public <T, R> R findOne(@NonNull PreparedQuery<T, R> preparedQuery) {
        System.out.println(">> findOne " + preparedQuery.getResultType().getName());
        System.out.println(">> findOne " + preparedQuery.getQuery());
        return super.findOne(preparedQuery);
    }
}
