package pg.micronaut.graal;

import io.micronaut.context.ApplicationContext;
import io.micronaut.context.env.Environment;
import io.micronaut.context.event.StartupEvent;
import io.micronaut.core.convert.ConversionContext;
import io.micronaut.core.convert.DefaultConversionService;
import io.micronaut.core.reflect.ClassUtils;
import io.micronaut.core.type.Argument;
import io.micronaut.inject.BeanDefinition;
import io.micronaut.inject.ExecutableMethod;
import io.micronaut.runtime.Micronaut;
import io.micronaut.runtime.event.annotation.EventListener;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.bridge.SLF4JBridgeHandler;
import pg.micronaut.graal.domain.model.Article;
import pg.micronaut.graal.domain.model.Dummy1;
import pg.micronaut.graal.domain.model.Dummy2;
import pg.micronaut.graal.domain.model.User;
import pg.micronaut.graal.domain.repository.DataArticleRepository;
import pg.micronaut.graal.domain.repository.DataDummy1Repository;
import pg.micronaut.graal.domain.repository.DataDummy2Repository;
import pg.micronaut.graal.domain.repository.DataUserRepository;
import pg.micronaut.graal.domain.repository.UserRepository;

import javax.inject.Singleton;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Slf4j
@Singleton
public class Application {

    private final ApplicationContext context;
    private final Environment env;

    private final UserRepository userRepository;
    private final DataArticleRepository articleRepository;
    private final DataDummy1Repository dummy1Repository;
    private final DataDummy2Repository dummy2Repository;

    public static void main(String[] args) {
        SLF4JBridgeHandler.removeHandlersForRootLogger();
        SLF4JBridgeHandler.install();
        Micronaut.run(Application.class, args);
    }

    @EventListener
    void init(StartupEvent event) {
//        dumpMetadata(DataUserRepository.class);
//        dumpMetadata(DataArticleRepository.class);
//        dumpMetadata(UserRepository.class);
        checkEnv();
        populateUsers();
        populateArticles();
        populateDummys1();
        populateDummys2();
    }

    private void checkEnv() {
        ClassLoader classLoader = Class.class.getClassLoader();
        if (classLoader == null) {
            classLoader = DefaultConversionService.class.getClassLoader();
        }
        log.info(">> cl: " + classLoader);

        String uuidType = "java.util.UUID";
        Optional<Class> uuidOptional = ClassUtils.forName(uuidType, classLoader);
        log.info(">> uuid: " + uuidOptional);

        String stringType = "java.lang.String";
        Optional<Class> stringOptional = ClassUtils.forName(stringType, classLoader);
        log.info(">> string: " + stringOptional);

        Optional<Class> uuidConverted = env.convert(uuidType, ConversionContext.of(Argument.of(Class.class)));
        log.info(">> uuidConverted: " + uuidConverted);

        Optional<Class> stringConverted = env.convert(stringType, ConversionContext.of(Argument.of(Class.class)));
        log.info(">> stringConverted: " + stringConverted);
    }

    private void populateUsers() {
        log.info(">> populate users start");
        try {
            userRepository.save(User.builder()
                    .id(UUID.randomUUID())
                    .username("u1")
                    .password("p1")
                    .email("u1@email.com")
                    .build());
        } catch (Exception ex) {
            log.error("failed to populate users", ex);
        }
        log.info(">> populate users end");
    }

    private void populateArticles() {
        log.info(">> populate articles start");
        try {
            Article article = Article.builder()
                    .id(UUID.randomUUID())
                    .title("t1")
                    .build();
            if (articleRepository.existsById(article.getId())) {
                articleRepository.update(article);
            } else {
                articleRepository.save(article);
            }
        } catch (Exception ex) {
            log.error("failed to populate articles", ex);
        }
        log.info(">> populate articles end");
    }

    private void populateDummys1() {
        log.info(">> populate dummys1 start");
        try {
            Dummy1 dummy1 = Dummy1.builder()
                    .id(LocalDate.now())
                    .title("t1")
                    .build();
            if (dummy1Repository.existsById(dummy1.getId())) {
                dummy1Repository.update(dummy1);
            } else {
                dummy1Repository.save(dummy1);
            }
        } catch (Exception ex) {
            log.error("failed to populate dummys1", ex);
        }
        log.info(">> populate dummys1 end");
    }

    private void populateDummys2() {
        log.info(">> populate dummys2 start");
        try {
            Dummy2 dummy2 = Dummy2.builder()
                    .id(UUID.randomUUID().toString())
                    .title("t1")
                    .build();
            if (dummy2Repository.existsById(dummy2.getId())) {
                dummy2Repository.update(dummy2);
            } else {
                dummy2Repository.save(dummy2);
            }
        } catch (Exception ex) {
            log.error("failed to populate dummys2", ex);
        }
        log.info(">> populate dummys2 end");
    }

    private void dumpMetadata(Class<?> bean) {
        log.info(">> dumping metadata " + bean.getName());
        BeanDefinition<?> beanDefinition = context.getBeanDefinition(bean);
        for (ExecutableMethod<?, ?> em : beanDefinition.getExecutableMethods()) {
            log.info(">> " + beanDefinition.getName() +  em.getName() + " : " + em.getClass().getName());
            for (String annotation : em.getAnnotationNames()) {
                log.info("\t\t>> [" + annotation + "] " + em.getAnnotationMetadata().getAnnotation(annotation).getValues());
            }
        }
    }

}
