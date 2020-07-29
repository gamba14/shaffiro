package com.shaffiro.service;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.vertx.core.Vertx;
import io.vertx.ext.web.client.WebClient;
import io.vertx.mqtt.messages.MqttPublishMessage;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.net.www.http.HttpClient;

import java.io.IOException;
import java.io.UnsupportedEncodingException;


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

    private final CloseableHttpClient httpClient = HttpClients.createDefault();

    public String processMessage(MqttPublishMessage inMsg) {
        try {
            return process(inMsg);
        } catch (UnsupportedEncodingException ex) {
            log.error(ex.getLocalizedMessage());
            return "fail";
        }
    }

    private String process(MqttPublishMessage inMsg) throws UnsupportedEncodingException {
        // formato del topic /dispositivo/id
        String[] topicParsed = inMsg.topicName().split("/");
        //aca vamos a tener que llamar al motor de reglas.
        HttpPost post = new HttpPost("http://docker_rules-engine_1:5000/ruleEngine/digestor");
        StringBuilder json = new StringBuilder();
        json.append("{");
        json.append("\"id\":\"");
        json.append(topicParsed[2] + "\",");
        json.append("\"pv\":\"");
        json.append(new String(inMsg.payload().getBytes()) + "\"");
        json.append("}");
        log.debug("Se envia al digestor: {}", json.toString());
        post.setEntity(new StringEntity(json.toString()));

        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(post)) {
            log.info(EntityUtils.toString(response.getEntity()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "{\"id\":\"5\",\"accion\":\"0\"}";
    }
}
