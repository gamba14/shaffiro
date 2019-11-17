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
       /* mqttServer.endpointHandler(endpoint -> {

            // shows main connect info
            log.debug("MQTT client [" + endpoint.clientIdentifier() + "] request to connect, clean session = " + endpoint.isCleanSession());

            if (endpoint.auth() != null) {
                log.debug("[username = " + endpoint.auth().getUsername() + ", password = " + endpoint.auth().getPassword() + "]");
            }
            if (endpoint.will() != null) {
                log.debug("[will topic = " + endpoint.will().getWillTopic() + " msg = " + new String(endpoint.will().getWillMessageBytes()) +
                    " QoS = " + endpoint.will().getWillQos() + " isRetain = " + endpoint.will().isWillRetain() + "]");
            }

            log.debug("[keep alive timeout = " + endpoint.keepAliveTimeSeconds() + "]");

            endpoint.publishHandler(mqttPublishMessage ->

                log.debug("Just received message " + mqttPublishMessage.payload().toString()+" ] with QoS ["+ mqttPublishMessage.qosLevel().toString() + "]")
            );
            // accept connection from the remote client
            endpoint.accept(false);

        })*/

        return mqttServer;
    }
}
