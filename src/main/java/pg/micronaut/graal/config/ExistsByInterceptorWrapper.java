package pg.micronaut.graal.config;

import edu.umd.cs.findbugs.annotations.NonNull;
import io.micronaut.aop.MethodInvocationContext;
import io.micronaut.context.annotation.Replaces;
import io.micronaut.data.intercept.ExistsByInterceptor;
import io.micronaut.data.intercept.RepositoryMethodKey;
import io.micronaut.data.intercept.annotation.DataMethod;
import io.micronaut.data.operations.RepositoryOperations;
import io.micronaut.data.runtime.intercept.DefaultExistsByInterceptor;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@Slf4j
@Replaces(ExistsByInterceptor.class)
public class ExistsByInterceptorWrapper<T> extends DefaultExistsByInterceptor<T> {

    protected ExistsByInterceptorWrapper(@NonNull RepositoryOperations datastore) {
        super(datastore);
    }

    @Override
    public Boolean intercept(RepositoryMethodKey methodKey, MethodInvocationContext<T, Boolean> context) {
        log.info(">> context: " + context.getExecutableMethod().getClass().getName() + " : " + context.toString());
        Optional<Class> aClass = context.classValue(DataMethod.class, DataMethod.META_MEMBER_ID_TYPE);
        log.info(">> aClass: " + aClass.map(Class::getName).orElse(null));
        Class idType = aClass.orElseGet(() -> getRequiredRootEntity(context));
        log.info(">> idType: " + idType.getName());
        return super.intercept(methodKey, context);
    }

}
