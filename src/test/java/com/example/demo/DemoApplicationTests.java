package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DemoApplicationTests extends AbstractTestContainersSetup {

    @Autowired
    private WebTestClient client;

    @Test
    void shouldSendAndGetMessage() {
        var input = "hello";
        client.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/echo")
                        .queryParam("input", input)
                        .build())
                .exchange()
                .expectBody(String.class)
                .value(s -> assertThat(s).isEqualTo(input));

    }

}
