package com.shaffiro.service;

import com.deliveredtechnologies.rulebook.FactMap;
import com.deliveredtechnologies.rulebook.NameValueReferableMap;
import com.deliveredtechnologies.rulebook.lang.RuleBookBuilder;
import com.deliveredtechnologies.rulebook.model.RuleBook;
import com.shaffiro.service.dto.DispositivoDTO;
import com.shaffiro.service.dto.ReglaDTO;
import io.netty.handler.codec.mqtt.MqttQoS;
import io.vertx.core.buffer.Buffer;
import io.vertx.mqtt.MqttEndpoint;
import io.vertx.mqtt.messages.MqttPublishMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

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

    public MQTTBrokerService(DispositivoService dispositivoService, ReglaService reglaService) {
        this.dispositivoService = dispositivoService;
        this.reglaService = reglaService;
    }

    public void handler(MqttEndpoint mqttEndpoint) {
        try {
            response = "0";
            log.debug("MQTT client [" + mqttEndpoint.clientIdentifier() + "] request to connect, clean session = "
                + mqttEndpoint.isCleanSession());
            clientID = mqttEndpoint.clientIdentifier();
            log.debug("[keep alive timeout = " + mqttEndpoint.keepAliveTimeSeconds() + "]");
            mqttEndpoint.accept(true).publish("actuador/2/accion",
                Buffer.buffer(response),
                MqttQoS.EXACTLY_ONCE,
                false,
                false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //publishHandler(this::handle)



    private void handle(MqttPublishMessage message) {
        response = null;
        log.debug("Received message [" + message.payload().toString() + "] from " + clientID);
        //Hago esto hasta tener implementacion a nivel DB
        //TODO fix it
        List<DispositivoDTO> dispositivosList = dispositivoService.findAll();
        DispositivoDTO dispositivoDTO = dispositivosList.stream()
            .filter(dispositivoDTO1 -> dispositivoDTO1.getNombre().equals(clientID)).collect(Collectors.toList()).get(0);
        log.debug(dispositivoDTO.toString());
        //Trae todas las reglas asociadas a ese dispositivo.
        List<ReglaDTO> reglaList = reglaService
            .findAll()
            .stream()
            .filter(reglaDTO -> reglaDTO.getId().equals(dispositivoDTO.getReglaId())).collect(Collectors.toList());

        RuleBook<Object> ruleBook = RuleBookBuilder.create()
            .addRule(rule -> rule.withFactType(String.class)
                .when(f -> f.containsKey(">")
                    || f.containsKey("<")
                    || f.containsKey("||")
                    || f.containsKey("&&")
                    || f.containsKey("=>"))
                .using(message.payload().toString()).then(this::output))
            .build();

        String[] parsedRule = reglaList.get(0).getLogica().split(" ");
        NameValueReferableMap<String> factMap = new FactMap<>();
        for(String s: parsedRule){
            factMap.setValue(s,s);
        }
        ruleBook.run(factMap);
    }

    private void output(NameValueReferableMap<String> string){
        log.debug("Hello im an action");
        response = "0";

    }
}
