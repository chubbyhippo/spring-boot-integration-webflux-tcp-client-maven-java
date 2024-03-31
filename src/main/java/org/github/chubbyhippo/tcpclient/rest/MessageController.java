package org.github.chubbyhippo.tcpclient.rest;

import org.github.chubbyhippo.tcpclient.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @GetMapping("/echo")
    Mono<String> getEcho(@RequestParam String input) {

        return messageService.echo(input);
    }
}
