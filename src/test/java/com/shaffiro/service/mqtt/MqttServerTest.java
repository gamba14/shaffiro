package com.shaffiro.service.mqtt;

import io.netty.handler.codec.mqtt.MqttQoS;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import io.vertx.mqtt.MqttClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author Agustin Gambirassi
 **/
//@SpringBootTest
@RunWith(VertxUnitRunner.class)
public class MqttServerTest extends AbstractVerticle {
    private static final String MQTT_TOPIC = "/my_topic";
    private static final String MQTT_MESSAGE = "hola mundo";
    private static final String BROKER_HOST = "localhost";
    private static final int BROKER_PORT = 1883;
    private static final Logger log = LoggerFactory.getLogger(MqttServerTest.class);

    @Test
    public void start(TestContext context) throws Exception {
        log.debug("Testing mqtt server");
        Async async = context.async();

        MqttClient mqttClient = MqttClient.create(Vertx.vertx());
        try {
            mqttClient.connect(BROKER_PORT, BROKER_HOST,ch -> {
                if (!ch.succeeded()) {
                    log.debug("Connected to a server");

                    mqttClient.publish(
                        MQTT_TOPIC,
                        Buffer.buffer(MQTT_MESSAGE),
                        MqttQoS.AT_MOST_ONCE,
                        false,
                        false,
                        s -> mqttClient.disconnect(d -> log.debug("Disconnected from server")));
                } else {
                    log.error("Failed to connect to a server");
                    log.error(String.valueOf(ch.cause()));
                }
            });
            async.complete();
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Error");
        }
    }

}
