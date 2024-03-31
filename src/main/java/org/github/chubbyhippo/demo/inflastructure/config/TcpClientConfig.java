package org.github.chubbyhippo.demo.inflastructure.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.ip.tcp.TcpOutboundGateway;
import org.springframework.integration.ip.tcp.connection.AbstractClientConnectionFactory;
import org.springframework.integration.ip.tcp.connection.CachingClientConnectionFactory;
import org.springframework.integration.ip.tcp.connection.TcpNioClientConnectionFactory;
import org.springframework.integration.ip.tcp.serializer.ByteArrayRawSerializer;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

@Configuration
public class TcpClientConfig {

    @Value("${tcp.server.host}")
    private String host;
    @Value("${tcp.server.port}")
    private int port;
    @Value("${tcp.client.so-timeout}")
    private int soTimeout;
    @Value("${tcp.client.connection.pool-size}")
    private int connectionPoolSize;

    @Bean
    AbstractClientConnectionFactory clientConnectionFactory() {
        var tcpNioClientConnectionFactory = new TcpNioClientConnectionFactory(host, port);
        tcpNioClientConnectionFactory.setUsingDirectBuffers(false);
        tcpNioClientConnectionFactory.setSerializer(new ByteArrayRawSerializer());
        tcpNioClientConnectionFactory.setDeserializer(new ByteArrayRawSerializer(true));
        tcpNioClientConnectionFactory.setSoTimeout(soTimeout);

        return new CachingClientConnectionFactory(tcpNioClientConnectionFactory, connectionPoolSize);
    }

    @Bean
    MessageChannel outBoundChannel() {
        return new DirectChannel();
    }

    @Bean
    @ServiceActivator(inputChannel = "outboundChannel")
    MessageHandler outboundGateway(AbstractClientConnectionFactory clientConnectionFactory) {
        var tcpOutboundGateway = new TcpOutboundGateway();
        tcpOutboundGateway.setConnectionFactory(clientConnectionFactory);

        return tcpOutboundGateway;
    }
}
