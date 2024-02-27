package com.example.demo;

import org.junit.ClassRule;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.testcontainers.containers.ComposeContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.File;
import java.time.Duration;

@Testcontainers
public abstract class AbstractTestContainersSetup {

    @ClassRule
    public static ComposeContainer environment =
            new ComposeContainer(new File("src/test/resources/compose.yaml"))
                    .withExposedService("app",
                            9876,
                            Wait.forListeningPort()
                                    .withStartupTimeout(Duration.ofMinutes(10)));

    @BeforeAll
    static void setUp() {
        environment.start();
    }

    @AfterAll
    static void tearDown() {
        environment.stop();
    }
}
