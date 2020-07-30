package com.shaffiro.service;

import io.vertx.mqtt.messages.MqttPublishMessage;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.IOException;


/**
 * @author Agustin Gambirassi
 **/
@Component
@Scope("prototype")
public class ReglasEngineService {
    private static final Logger log = LoggerFactory.getLogger(ReglasEngineService.class);
    private final DispositivoService dispositivoService;


    public ReglasEngineService(DispositivoService dispositivoService) {
        this.dispositivoService = dispositivoService;
    }

    public void processMessage(MqttPublishMessage inMsg) {
        process(inMsg);
    }

    private void process(MqttPublishMessage inMsg) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        String[] topicParsed = inMsg.topicName().split("/");
        HttpPost post = new HttpPost("http://docker_rules-engine_1:5000/ruleEngine/digestor");
        StringBuilder json = new StringBuilder();
        json.append("{");
        json.append("\"id\":\"");
        json.append(topicParsed[2] + "\",");
        json.append("\"pv\":\"");
        json.append(new String(inMsg.payload().getBytes()) + "\"");
        json.append("}");
        log.debug("Se envia al digestor: {}", json.toString());
        try {
            post.setEntity(new StringEntity(json.toString()));
            httpClient.execute(post);
        } catch (IOException e) {
            e.printStackTrace();
            log.error("MA, me lastime");
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
                log.error("MA, no puedo cerrar la conexion.");
            }
        }
    }
}
