package com.xmut.forum.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * WebScoket配置处理器
 */
@Configuration
public class WebSocketConfig {
    @Bean
    public ServerEndpointExporter serverEndPointExporter() {
        return new ServerEndpointExporter();
    }
}