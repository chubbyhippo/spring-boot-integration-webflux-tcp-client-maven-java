FROM alpine/git AS code
WORKDIR /application
RUN git clone https://github.com/chubbyhippo/spring-boot-reactor-tcp-echo-server-gradle-java.git mock-echo-tcp-server

FROM bellsoft/liberica-openjdk-alpine:21 AS builder
COPY --from=code /application /application
WORKDIR application/mock-echo-tcp-server
RUN ./gradlew bootJar
RUN java -Djarmode=layertools -jar build/libs/*.jar extract

FROM bellsoft/liberica-openjre-alpine:21
WORKDIR /application
COPY --from=builder application/mock-echo-tcp-server/dependencies .
COPY --from=builder application/mock-echo-tcp-server/spring-boot-loader .
COPY --from=builder application/mock-echo-tcp-server/snapshot-dependencies .
COPY --from=builder application/mock-echo-tcp-server/application .
ENTRYPOINT ["java", "org.springframework.boot.loader.launch.JarLauncher"]