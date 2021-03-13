package pg.micronaut.graal.config;

import edu.umd.cs.findbugs.annotations.NonNull;
import io.micronaut.aop.MethodInvocationContext;
import io.micronaut.context.annotation.Replaces;
import io.micronaut.core.annotation.AnnotationValue;
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
        log.info(">> context: " + context.getExecutableMethod().getClass().getName() + " : " + context.toString());

        AnnotationValue<Annotation> annotation = context.getAnnotationMetadata().getAnnotation("io.micronaut.data.intercept.annotation.DataMethod");
        log.info(">> annotation: " + annotation);

        Map<CharSequence, Object> values = annotation.getValues();
        log.info(">> DataMethod values: " + values);

        Optional<AnnotationValue<Annotation>> annotationValueOptional = context.getAnnotationMetadata().findAnnotation(DataMethod.class.getName());
        log.info(">> annotationValueOptional: " + annotationValueOptional.toString());
        log.info(">> annotationValue string: " + annotationValueOptional.flatMap(av -> av.stringValue(DataMethod.META_MEMBER_ID_TYPE)).orElse(null));

        Optional<Class> classOptional1 = annotationValueOptional.flatMap(av -> av.get(DataMethod.META_MEMBER_ID_TYPE, Argument.of(Class.class)));
        log.info(classOptional1.toString());

        Optional<Class> classOptional2 = context.getAnnotationMetadata().getValue(DataMethod.class.getName(), DataMethod.META_MEMBER_ID_TYPE, Argument.of(Class.class));
        log.info(classOptional2.toString());

        Optional<Class> classOptional3 = context.getAnnotationMetadata().classValue(DataMethod.class.getName(), DataMethod.META_MEMBER_ID_TYPE);
        log.info(classOptional3.toString());

        log.info(">> idType string: " + annotation.stringValue(DataMethod.META_MEMBER_ID_TYPE));
        log.info(">> idType class: " + annotation.classValue(DataMethod.META_MEMBER_ID_TYPE));

        Optional<Class> aClass = context.classValue(DataMethod.class, DataMethod.META_MEMBER_ID_TYPE);
        log.info(">> aClass: " + aClass.map(Class::getName).orElse(null));

        Class idType = aClass.orElseGet(() -> getRequiredRootEntity(context));
        log.info(">> idType: " + idType.getName());

        return super.intercept(methodKey, context);
    }

}
