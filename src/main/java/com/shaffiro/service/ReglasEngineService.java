package com.shaffiro.service;

import io.vertx.mqtt.messages.MqttPublishMessage;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.TimeUnit;


/**
 * @author Agustin Gambirassi
 **/
@Component
@Scope("prototype")
public class ReglasEngineService {
    private static final Logger log = LoggerFactory.getLogger(ReglasEngineService.class);
    private final DispositivoService dispositivoService;

    public static final MediaType JSON
        = MediaType.get("application/json; charset=utf-8");

    public ReglasEngineService(DispositivoService dispositivoService) {
        this.dispositivoService = dispositivoService;
    }

    public void processMessage(MqttPublishMessage inMsg) throws IOException {
        process(inMsg);
    }

    private void process(MqttPublishMessage inMsg) throws IOException {
        OkHttpClient client = new OkHttpClient.Builder()
            .callTimeout(1500, TimeUnit.MILLISECONDS)
            .build();
        String url = "http://docker_rules-engine_1:5000/ruleEngine/digestor";

        String[] topicParsed = inMsg.topicName().split("/");
        StringBuilder json = new StringBuilder();
        json.append("{");
        json.append("\"id\":\"");
        json.append(topicParsed[2] + "\",");
        json.append("\"pv\":\"");
        json.append(new String(inMsg.payload().getBytes()) + "\"");
        json.append("}");
        RequestBody body = RequestBody.create(json.toString(), JSON);


        log.debug("Se envia al digestor: {}", json.toString());
        Request request = new Request.Builder()
            .url(url)
            .post(body)
            .build();
        client.newCall(request).execute();


    }
}
