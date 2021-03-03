package pg.micronaut.graal.controller;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Put;
import lombok.RequiredArgsConstructor;
import pg.micronaut.graal.domain.model.Article;
import pg.micronaut.graal.domain.repository.DataArticleRepository;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Controller("/api")
public class ArticleController {

    private final DataArticleRepository articleRepository;

    @Post("/articles")
    HttpResponse<Article> create(Map<String, String> command) {
        Article article = Article.builder()
                .id(UUID.randomUUID())
                .title(command.get("title"))
                .build();
        return HttpResponse.ok(articleRepository.save(article));
    }

    @Get("/articles/{id}")
    HttpResponse<Article> get(@PathVariable UUID id) {
        Optional<Article> articleOptional = articleRepository.findById(id);
        if (articleOptional.isPresent()) {
            return HttpResponse.ok(articleOptional.get());
        }
        return HttpResponse.notFound();
    }

    @Put("/articles/{id}")
    HttpResponse<Article> update(@PathVariable UUID id, Map<String, String> command) {
        Optional<Article> articleOptional = articleRepository.findById(id);
        if (articleOptional.isPresent()) {
            Article article = articleOptional.get();
            Article alteredArticle = article.toBuilder()
                    .title(command.get("title"))
                    .build();
            return HttpResponse.ok(articleRepository.save(alteredArticle));
        }
        return HttpResponse.notFound();
    }

}
