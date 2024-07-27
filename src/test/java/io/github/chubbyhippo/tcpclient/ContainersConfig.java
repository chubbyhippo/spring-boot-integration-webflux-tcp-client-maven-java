package io.github.chubbyhippo.tcpclient;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.testcontainers.containers.ComposeContainer;
import org.testcontainers.containers.wait.strategy.Wait;

import java.io.File;
import java.time.Duration;

@TestConfiguration(proxyBeanMethods = false)
public class ContainersConfig {

    @Bean
    ComposeContainer composeContainer(DynamicPropertyRegistry registry) {

        var compose = new ComposeContainer(new File("compose.yaml"))
                .withExposedService("mock-tcpserver",
                        9876,
                        Wait.forListeningPort()
                                .withStartupTimeout(Duration.ofMinutes(10)))
                .withLocalCompose(true);

        registry.add("tcp.server.host", () -> compose.getServiceHost("mock-tcpserver", 9876));
        registry.add("tcp.server.port", () -> compose.getServicePort("mock-tcpserver", 9876));

        return compose;
    }
}
