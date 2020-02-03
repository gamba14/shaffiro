package com.shaffiro.service;

import com.deliveredtechnologies.rulebook.FactMap;
import com.deliveredtechnologies.rulebook.NameValueReferableMap;
import com.deliveredtechnologies.rulebook.lang.RuleBookBuilder;
import com.deliveredtechnologies.rulebook.model.RuleBook;
import com.shaffiro.config.MQTTConfiguration;
import com.shaffiro.service.dto.DispositivoDTO;
import com.shaffiro.service.dto.ReglaDTO;
import io.netty.handler.codec.mqtt.MqttQoS;
import io.vertx.core.buffer.Buffer;
import io.vertx.mqtt.MqttEndpoint;
import io.vertx.mqtt.MqttServer;
import io.vertx.mqtt.MqttTopicSubscription;
import io.vertx.mqtt.messages.MqttPublishMessage;
import io.vertx.mqtt.messages.MqttSubscribeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * @author Agustin Gambirassi
 **/
@Service
public class MQTTBrokerService {
    private static final Logger log = LoggerFactory.getLogger(MQTTBrokerService.class);
    private final DispositivoService dispositivoService;
    private final ReglaService reglaService;
    private String clientID;
    private String response;
    private MqttEndpoint endpoint;


    public MQTTBrokerService(DispositivoService dispositivoService, ReglaService reglaService) {
        this.dispositivoService = dispositivoService;
        this.reglaService = reglaService;
    }

    public void handler(MqttEndpoint mqttEndpoint) {
        try {
            this.endpoint = mqttEndpoint;
            response = "0";
            log.debug("MQTT client [" + mqttEndpoint.clientIdentifier() + "] request to connect, clean session = "
                + mqttEndpoint.isCleanSession());
            clientID = mqttEndpoint.clientIdentifier();
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
        log.debug("ACK for messageID " + integer );
    }


    private void handleSubscription(MqttSubscribeMessage mqttSubscribeMessage) {
        List<MqttQoS> grantedQosLevels = new ArrayList<>();
        for(MqttTopicSubscription s: mqttSubscribeMessage.topicSubscriptions()){
            log.debug("Subscription for " + s.topicName() + " with QoS " + s.qualityOfService());
            grantedQosLevels.add(s.qualityOfService());
        }
//        .subscribeAcknowledge(mqttSubscribeMessage.messageId(), grantedQosLevels);
    }
    //publishHandler(this::handle)



    private void handle(MqttPublishMessage message) {
        log.debug("Received message [" + message.payload().toString() + "] from " /*+ message.clientID*/);
        //Hago esto hasta tener implementacion a nivel DB
        //TODO fix it
//        List<DispositivoDTO> dispositivosList = dispositivoService.findAll();
//        DispositivoDTO dispositivoDTO = dispositivosList.stream()
//            .filter(dispositivoDTO1 -> dispositivoDTO1.getNombre().equals(clientID)).collect(Collectors.toList()).get(0);
//        log.debug(dispositivoDTO.toString());
        publish(message);

    }

    private void publish(MqttPublishMessage message){
        log.debug("Publicando mensaje para: " + message.topicName());
        log.debug("Contenido: " + message.payload().toString());
        endpoint.publish(message.topicName(), Buffer.buffer("Respuesta desde server"), MqttQoS.AT_LEAST_ONCE,false,false);
    }

}
