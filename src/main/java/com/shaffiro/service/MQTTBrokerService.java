package com.shaffiro.service;


import com.shaffiro.service.dto.ProcessValueDTO;
import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.mqtt.MqttQoS;
import io.vertx.core.buffer.Buffer;
import io.vertx.mqtt.MqttEndpoint;
import io.vertx.mqtt.MqttTopicSubscription;
import io.vertx.mqtt.messages.MqttPublishMessage;
import io.vertx.mqtt.messages.MqttSubscribeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Agustin Gambirassi
 **/
@Service
public class MQTTBrokerService {
    private static final Logger log = LoggerFactory.getLogger(MQTTBrokerService.class);
    private final ReglasEngineService reglasEngineService;
    private MqttEndpoint endpoint;


    public MQTTBrokerService(ReglasEngineService reglasEngineService) {
        this.reglasEngineService = reglasEngineService;
    }

    public void handler(MqttEndpoint mqttEndpoint) {
        try {
            this.endpoint = mqttEndpoint;
            log.debug("MQTT client [" + mqttEndpoint.clientIdentifier() + "] request to connect, clean session = "
                + mqttEndpoint.isCleanSession());
            String clientID = mqttEndpoint.clientIdentifier();
            log.debug("[keep alive timeout = " + mqttEndpoint.keepAliveTimeSeconds() + "]");
            mqttEndpoint.accept(true);
//            .publish("actuador/2/accion",
//                Buffer.buffer(response),
//                MqttQoS.EXACTLY_ONCE,
//                false,
//                false);
            mqttEndpoint.publishHandler(this::handle);
            mqttEndpoint.subscribeHandler(this::handleSubscription);
            mqttEndpoint.publishAcknowledgeHandler(this::publishAck);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void publishAck(Integer integer) {
        log.debug("ACK for messageID " + integer);
    }


    private void handleSubscription(MqttSubscribeMessage mqttSubscribeMessage) {
        List<MqttQoS> grantedQosLevels = new ArrayList<>();
        for (MqttTopicSubscription s : mqttSubscribeMessage.topicSubscriptions()) {
            log.debug("Subscription for " + s.topicName() + " with QoS " + s.qualityOfService());
            grantedQosLevels.add(s.qualityOfService());
        }
//        .subscribeAcknowledge(mqttSubscribeMessage.messageId(), grantedQosLevels);
    }
    //publishHandler(this::handle)


    private void handle(MqttPublishMessage message) {
        log.debug("Received message [" + message.payload().toString() + "] from " /*+ message.clientID*/);

        publish(message, reglasEngineService.processMessage(message));

    }

    private void publish(MqttPublishMessage message, String payload) {
//        log.debug("Recibiendo mensaje de: " + message.topicName());
        log.debug("Contenido: " + message.payload().toString());
        //llamar al motor de reglas
        endpoint.publish(message.topicName(), Buffer.buffer(payload), MqttQoS.AT_LEAST_ONCE, false, false);
    }

    public void sendMessage(ProcessValueDTO dto) {
        ByteBuf buff = null;
        buff.setBytes(0, dto.getAction().getBytes());
        MqttPublishMessage message = MqttPublishMessage
            .create(1, MqttQoS.AT_LEAST_ONCE, false, false, "/ACTUADOR/" + dto.getId(), buff);
        publish(message, dto.getAction());

    }
}
