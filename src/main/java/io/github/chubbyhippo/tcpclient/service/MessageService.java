package io.github.chubbyhippo.tcpclient.service;

import io.github.chubbyhippo.tcpclient.inflastructure.service.TcpClientService;
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
