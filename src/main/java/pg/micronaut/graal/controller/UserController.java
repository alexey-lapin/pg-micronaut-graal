package pg.micronaut.graal.controller;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import lombok.RequiredArgsConstructor;
import pg.micronaut.graal.domain.repository.UserRepository;
import pg.micronaut.graal.domain.model.User;

import java.util.Map;
import java.util.UUID;

@RequiredArgsConstructor
@Controller("/api")
public class UserController {

    private final UserRepository userRepository;

//    @Override
//    public LoginUserResult login(@Valid LoginUser command) {
//        return bus.executeCommand(command);
//    }

    @Post("/users")
    public String register(Map<String, String> command) {
        User user = User.builder()
                .id(UUID.randomUUID())
                .username(command.get("username"))
                .email(command.get("email"))
                .password(command.get("password"))
                .build();
        userRepository.save(user);
        return user.toString();
    }

//    @Override
//    public GetCurrentUserResult current() {
//        return bus.executeQuery(new GetCurrentUser(auth.currentUsername()));
//    }

//    @Override
//    public UpdateUserResult update(@Valid UpdateUser command) {
//        return bus.executeCommand(command.withCurrentUsername(auth.currentUsername()));
//    }

}