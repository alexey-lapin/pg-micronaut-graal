package pg.micronaut.graal;

import io.micronaut.context.event.StartupEvent;
import io.micronaut.runtime.Micronaut;
import io.micronaut.runtime.event.annotation.EventListener;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pg.micronaut.graal.domain.model.Article;
import pg.micronaut.graal.domain.model.User;
import pg.micronaut.graal.domain.repository.DataArticleRepository;
import pg.micronaut.graal.domain.repository.UserRepository;

import javax.inject.Singleton;
import java.util.UUID;

@RequiredArgsConstructor
@Slf4j
@Singleton
public class Application {

    private final UserRepository userRepository;
    private final DataArticleRepository articleRepository;

    public static void main(String[] args) {
        Micronaut.run(Application.class, args);
    }

    @EventListener
    void init(StartupEvent event) {
        log.info("populating data");
        try {
            userRepository.save(User.builder()
                    .id(UUID.randomUUID())
                    .username("u1")
                    .password("p1")
                    .email("u1@email.com")
                    .build());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        try {
            articleRepository.save(Article.builder()
                    .id(UUID.randomUUID())
                    .title("t1")
                    .build());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
