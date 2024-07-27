package io.github.chubbyhippo.tcpclient.inflastructure.service;

import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.stereotype.Service;

@Service
@MessagingGateway(defaultRequestChannel = "outboundChannel")
public interface TcpClientService {
    byte[] send(byte[] message);
}
