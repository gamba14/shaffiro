package com.shaffiro.config;

import com.shaffiro.service.DiscoveryService;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.datagram.DatagramSocket;
import io.vertx.core.datagram.DatagramSocketOptions;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Agustin Gambirassi
 **/
@Configuration
public class VertxConfiguration {
    private final Logger log = LoggerFactory.getLogger(VertxConfiguration.class);
    @Autowired
    private DiscoveryService discoveryService;

    @Bean
    public HttpServer init(){
        log.debug("Discovery service is listening on port 6788 TCP");
        Vertx vertx = Vertx.vertx();
        Router router = Router.router(vertx);
        router.route().handler(BodyHandler.create());
        router.post().handler(discoveryService::handle);

        return vertx.createHttpServer().requestHandler(router).listen(6788);
    }

    @Bean
    public DatagramSocket initSocket() {
        log.info("Discovery service listening on port 6789 UDP");
        DatagramSocketOptions options = new DatagramSocketOptions();
        options.setReceiveBufferSize(255);
        Vertx vertx = Vertx.vertx();
        Router router = Router.router(vertx);
        router.route().handler(BodyHandler.create());
        router.post().handler(discoveryService::handle);
        DatagramSocket socket = vertx.createDatagramSocket(options);
        socket.listen(6789, "0.0.0.0", asyncResult -> {
            if (asyncResult.succeeded()) {
                socket.handler(discoveryService::handleUDP);
            };
        });

        return socket;
    }
}
