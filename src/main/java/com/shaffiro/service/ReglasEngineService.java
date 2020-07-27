package com.shaffiro.service;

import io.vertx.mqtt.messages.MqttPublishMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


/**
 * @author Agustin Gambirassi
 **/
@Service
public class ReglasEngineService {
    private static final Logger log = LoggerFactory.getLogger(ReglasEngineService.class);
    private final DispositivoService dispositivoService;

    public ReglasEngineService(DispositivoService dispositivoService) {
        this.dispositivoService = dispositivoService;
    }

    public String processMessage(MqttPublishMessage inMsg) {
        return process(inMsg);
    }

    private String process(MqttPublishMessage inMsg) {
        // formato del topic /dispositivo/id
        String[] topicParsed = inMsg.topicName().split("/");
        //aca vamos a tener que llamar al motor de reglas.
        return "nada";
    }
}
