package com.shaffiro.config;

import io.vertx.core.Vertx;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Agustin Gambirassi
 **/
public class DiscoveryServerConfiguration {
    @Bean
    public Vertx httpServer(){
        Vertx vertx = Vertx.vertx();
        vertx.createHttpServer().listen(6789);
        return vertx;
    }
}
