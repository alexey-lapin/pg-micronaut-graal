package pg.micronaut.graal;

import io.micronaut.context.event.StartupEvent;
import io.micronaut.runtime.Micronaut;
import io.micronaut.runtime.event.annotation.EventListener;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pg.micronaut.graal.domain.model.User;
import pg.micronaut.graal.domain.repository.UserRepository;

import javax.inject.Singleton;
import java.util.UUID;

@RequiredArgsConstructor
@Slf4j
@Singleton
public class Application {

    private final UserRepository userRepository;

    public static void main(String[] args) {
        Micronaut.run(Application.class, args);
    }

    @EventListener
    void init(StartupEvent event) {
        log.info("populating data");
        userRepository.save(User.builder()
                .id(UUID.randomUUID())
                .username("u1")
                .password("p1")
                .email("u1@email.com")
                .build());
    }

}
