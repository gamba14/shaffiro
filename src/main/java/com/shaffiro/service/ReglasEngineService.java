package com.shaffiro.service;

import com.shaffiro.service.dto.ReglaDTO;
import io.netty.handler.codec.mqtt.MqttQoS;
import io.vertx.core.buffer.Buffer;
import io.vertx.mqtt.messages.MqttPublishMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * @author Agustin Gambirassi
 **/
@Service
public class ReglasEngineService {
    private static final Logger log = LoggerFactory.getLogger(ReglasEngineService.class);
    private final ReglaService reglaService;

    public ReglasEngineService(ReglaService reglaService) {
        this.reglaService = reglaService;
    }

    public MqttPublishMessage processMessage(MqttPublishMessage inMsg){
        return inMsg;
    }

    private void process(MqttPublishMessage inMsg){
        List<ReglaDTO> reglaDTOS = reglaService.findAll();

    }
}
