package com.github.xini1.intergration;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.xini1.Application;
import com.github.xini1.InMemoryFilmDescriptions;
import com.github.xini1.application.dto.JsonFilmDto;
import com.github.xini1.application.dto.JsonPage;
import com.github.xini1.port.FilmDescriptions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.web.util.UriComponentsBuilder;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

import java.net.URI;
import java.util.List;

/**
 * @author Maxim Tereshchenko
 */
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Import(IntegrationTest.Config.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
final class IntegrationTest {

    private static final PostgreSQLContainer<?> POSTGRES =
            new PostgreSQLContainer<>(DockerImageName.parse("postgres:14"))
                    .withInitScript("schema.sql");

    @Autowired
    private TestRestTemplate testRestTemplate;

    @DynamicPropertySource
    static void postgresProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", POSTGRES::getJdbcUrl);
        registry.add("spring.datasource.username", POSTGRES::getUsername);
        registry.add("spring.datasource.password", POSTGRES::getPassword);
    }

    @BeforeAll
    static void setUp() {
        POSTGRES.start();
    }

    @AfterAll
    static void tearDown() {
        POSTGRES.stop();
    }

    @Test
    @Order(0)
    void userCanSearchForFilm() {
        assertThat(testRestTemplate.exchange(searchRequest(), JsonPage.class).getBody()).isEqualTo(expectedPage());
    }

    @Test
    @Order(1)
    void userCanRateFilm() {
        assertThat(testRestTemplate.exchange(rateRequest(), void.class).getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @Order(2)
    void userCanViewTop10Films() {
        assertThat(
                testRestTemplate.exchange(top10Request(), new ParameterizedTypeReference<List<JsonFilmDto>>() {})
                        .getBody()
        )
                .containsOnly(expectedFilm());
    }

    private RequestEntity<Object> searchRequest() {
        return new RequestEntity<>(
                apiTokenHeaders(),
                HttpMethod.GET,
                UriComponentsBuilder.fromUriString("/films")
                        .queryParam("search", "Slumdog Millionaire")
                        .queryParam("page", 0)
                        .build()
                        .toUri()
        );
    }

    private JsonFilmDto expectedFilm() {
        return expectedFilm(10);
    }

    private JsonFilmDto expectedFilm(int rating) {
        return new JsonFilmDto(
                "id",
                "Slumdog Millionaire",
                true,
                rating,
                0
        );
    }

    private RequestEntity<Object> top10Request() {
        return new RequestEntity<>(
                apiTokenHeaders(),
                HttpMethod.GET,
                URI.create("/films/top")
        );
    }

    private RequestEntity<String> rateRequest() {
        return new RequestEntity<>(
                "10",
                rateRequestHeaders(),
                HttpMethod.POST,
                URI.create("/films/id/ratings")
        );
    }

    private HttpHeaders rateRequestHeaders() {
        var httpHeaders = new HttpHeaders();
        httpHeaders.addAll(apiTokenHeaders());
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return httpHeaders;
    }

    private HttpHeaders apiTokenHeaders() {
        var httpHeaders = new HttpHeaders();
        httpHeaders.set("apiToken", "token");
        return httpHeaders;
    }

    private JsonPage expectedPage() {
        return new JsonPage(
                List.of(expectedFilm(0)),
                0,
                1
        );
    }

    @TestConfiguration
    static class Config {

        @Bean
        FilmDescriptions filmDescriptions() {
            return new InMemoryFilmDescriptions(
                    new InMemoryFilmDescriptions.Stub("id", "Slumdog Millionaire")
            );
        }
    }
}
