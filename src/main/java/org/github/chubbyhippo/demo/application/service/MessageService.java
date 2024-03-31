package org.github.chubbyhippo.demo.application.service;

import org.github.chubbyhippo.demo.inflastructure.service.TcpClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final TcpClientService tcpClientService;

    public Mono<String> echo(String input) {

        return Mono.just(tcpClientService.send(input.getBytes()))
                .map(String::new);

    }
}
