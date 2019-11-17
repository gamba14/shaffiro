package com.shaffiro.service;

import io.vertx.mqtt.MqttEndpoint;
import io.vertx.mqtt.messages.MqttPublishMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @author Agustin Gambirassi
 **/
@Service
public class MQTTBrokerService {
    private static final Logger log = LoggerFactory.getLogger(MQTTBrokerService.class);

    public void handler(MqttEndpoint mqttEndpoint) {
        log.debug("MQTT client [" + mqttEndpoint.clientIdentifier() + "] request to connect, clean session = " + mqttEndpoint.isCleanSession());

        if (mqttEndpoint.auth() != null) {
            log.debug("[username = " + mqttEndpoint.auth().getUsername() + ", password = " + mqttEndpoint.auth().getPassword() + "]");
        }
//        if (mqttEndpoint.will() != null) {
//            log.debug("[will topic = " + mqttEndpoint.will().getWillTopic() + " msg = " + new String(mqttEndpoint.will().getWillMessageBytes()) +
//                " QoS = " + mqttEndpoint.will().getWillQos() + " isRetain = " + mqttEndpoint.will().isWillRetain() + "]");
//        }
        log.debug("[keep alive timeout = " + mqttEndpoint.keepAliveTimeSeconds() + "]");
        mqttEndpoint.publishHandler(this::handleMessage);
        mqttEndpoint.accept(true);

    }

    private void handleMessage(MqttPublishMessage message){
        log.debug("Received message [" + message.payload().toString()+"]");
    }

}
