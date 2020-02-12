package com.shaffiro.service;

import com.shaffiro.service.dto.DispositivoDTO;
import com.shaffiro.service.dto.ReglaDTO;
import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.mqtt.MqttQoS;
import io.vertx.core.buffer.Buffer;
import io.vertx.mqtt.messages.MqttPublishMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;


/**
 * @author Agustin Gambirassi
 **/
@Service
public class ReglasEngineService {
    private static final Logger log = LoggerFactory.getLogger(ReglasEngineService.class);
    private final ReglaService reglaService;
    private final DispositivoService dispositivoService;

    public ReglasEngineService(ReglaService reglaService, DispositivoService dispositivoService) {
        this.reglaService = reglaService;
        this.dispositivoService = dispositivoService;
    }

    public String processMessage(MqttPublishMessage inMsg) {
        return process(inMsg);
    }

    private String process(MqttPublishMessage inMsg) {
        Long id = Long.parseLong("1");
        MqttPublishMessage response;
        Optional<DispositivoDTO> dispositivoDTO = dispositivoService.findOne(id);
        Optional<Set<ReglaDTO>> reglaDTOSet = Optional.of(dispositivoDTO.get().getReglas());
        for (ReglaDTO regla : reglaDTOSet.get()) {
            log.debug("Regla encontrada " + regla.toString());
            Integer valorSeteado = Integer.parseInt(regla.getValor());
            Integer valorMedido = Integer.parseInt(inMsg.payload().toString());
            switch (regla.getOperador()) {
                case ">":
                    if (valorMedido > valorSeteado) {
                        return "0";
                    }
                    break;
                case "<":
                    if (valorMedido < valorSeteado) {
                        return "1";
                    }
                    break;
                case ">=":
                    if (valorMedido >= valorSeteado) {
                        return "0";
                    }
                    break;
                case "<=":
                    if (valorMedido <= valorSeteado) {
                        return "1";
                    }
                    break;
            }

        }
        return "N";
    }
}
