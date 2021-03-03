package pg.micronaut.graal.controller;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Put;
import lombok.RequiredArgsConstructor;
import pg.micronaut.graal.domain.model.User;
import pg.micronaut.graal.domain.repository.UserRepository;

import java.util.Map;
import java.util.Optional;
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
    public HttpResponse<User> register(Map<String, String> command) {
        User user = User.builder()
                .id(UUID.randomUUID())
                .username(command.get("username"))
                .email(command.get("email"))
                .password(command.get("password"))
                .build();
        return HttpResponse.ok(userRepository.save(user));
    }

    @Get("/users/{username}")
    public HttpResponse<User> get(@PathVariable String username) {
        Optional<User> userOptional = userRepository.findByUsername(username);
        if (userOptional.isPresent()) {
            return HttpResponse.ok(userOptional.get());
        }
        return HttpResponse.notFound();
    }

    @Put("/users/{username}")
    public HttpResponse<User> update(@PathVariable String username, Map<String, String> command) {
        Optional<User> userOptional = userRepository.findByUsername(username);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            User alteredUser = user.toBuilder()
                    .username(command.get("username"))
                    .build();
            return HttpResponse.ok(userRepository.save(alteredUser));
        }
        return HttpResponse.notFound();
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