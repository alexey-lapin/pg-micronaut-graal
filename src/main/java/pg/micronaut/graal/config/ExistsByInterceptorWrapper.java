package pg.micronaut.graal.config;

import edu.umd.cs.findbugs.annotations.NonNull;
import io.micronaut.aop.MethodInvocationContext;
import io.micronaut.context.annotation.Replaces;
import io.micronaut.core.annotation.AnnotationValue;
import io.micronaut.core.convert.ArgumentConversionContext;
import io.micronaut.core.convert.ConversionContext;
import io.micronaut.core.convert.value.ConvertibleValues;
import io.micronaut.core.type.Argument;
import io.micronaut.data.intercept.ExistsByInterceptor;
import io.micronaut.data.intercept.RepositoryMethodKey;
import io.micronaut.data.intercept.annotation.DataMethod;
import io.micronaut.data.operations.RepositoryOperations;
import io.micronaut.data.runtime.intercept.DefaultExistsByInterceptor;
import lombok.extern.slf4j.Slf4j;

import java.lang.annotation.Annotation;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Replaces(ExistsByInterceptor.class)
public class ExistsByInterceptorWrapper<T> extends DefaultExistsByInterceptor<T> {

    protected ExistsByInterceptorWrapper(@NonNull RepositoryOperations datastore) {
        super(datastore);
    }

    @Override
    public Boolean intercept(RepositoryMethodKey methodKey, MethodInvocationContext<T, Boolean> context) {
        Optional<Class> idType = context.classValue(DataMethod.class, DataMethod.META_MEMBER_ID_TYPE);
        log.info(">> context idType: " + idType.map(Class::getName).orElse(null));

        Class rootEntityType = idType.orElseGet(() -> getRequiredRootEntity(context));
        log.info(">> context rootEntityType: " + rootEntityType.getName());

        return super.intercept(methodKey, context);
    }

}
