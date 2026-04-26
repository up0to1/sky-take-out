package com.sky.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

@Configuration

public class WebSocketConfiguration {

    @Bean
    @ConditionalOnWebApplication
    @ConditionalOnProperty(name = "sky.websocket.enabled", havingValue = "true", matchIfMissing = true)
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }
}
