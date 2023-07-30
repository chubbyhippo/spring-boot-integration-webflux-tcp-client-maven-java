package com.example.demo.application.service;

import com.example.demo.inflastructure.service.TcpClientService;
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
