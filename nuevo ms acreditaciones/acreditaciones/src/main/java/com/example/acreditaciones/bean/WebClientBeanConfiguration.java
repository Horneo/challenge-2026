package com.example.acreditaciones.bean;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;

@Configuration
public class WebClientBeanConfiguration {

    @Bean
    public WebClient posWebClient(
            @Value("${clients.pos.base-url}") String baseUrl,
            @Value("${clients.pos.connect-timeout-ms:2000}") int connectTimeoutMs,
            @Value("${clients.pos.read-timeout-ms:3000}") int readTimeoutMs,
            @Value("${clients.pos.write-timeout-ms:3000}") int writeTimeoutMs
    ) {
        HttpClient httpClient = HttpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, connectTimeoutMs)
                .responseTimeout(Duration.ofMillis(readTimeoutMs))
                .doOnConnected(conn ->
                        conn.addHandlerLast(new ReadTimeoutHandler(readTimeoutMs / 1000))
                                .addHandlerLast(new WriteTimeoutHandler(writeTimeoutMs / 1000))
                );

        ExchangeStrategies strategies = ExchangeStrategies.builder()
                .codecs(cfg -> cfg.defaultCodecs().maxInMemorySize(4 * 1024 * 1024)) // 4MB
                .build();

        return WebClient.builder()
                .baseUrl(baseUrl)
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .exchangeStrategies(strategies)
                .defaultHeader("Accept", "application/json")
                .build();
    }
}

