package com.shaffiro.config;

import com.shaffiro.service.MQTTBrokerService;
import io.vertx.core.Vertx;
import io.vertx.mqtt.MqttServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Agustin Gambirassi
 **/
@Configuration
public class MQTTConfiguration {
    private static final Logger log = LoggerFactory.getLogger(MQTTConfiguration.class);
    @Autowired
    private MQTTBrokerService mqttBrokerService;

    @Bean
    public MqttServer mqttServer(){
        MqttServer mqttServer = MqttServer.create(Vertx.vertx());
        mqttServer.endpointHandler(mqttBrokerService::handler).listen(ar -> {

            if (ar.succeeded()) {

                log.debug("MQTT server is listening on port " + ar.result().actualPort());
            } else {

                log.debug("Error on starting the server");
                ar.cause().printStackTrace();
            }
        });
        return mqttServer;
    }
}
