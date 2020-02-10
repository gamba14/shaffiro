package com.shaffiro.service;

import io.vertx.mqtt.MqttEndpoint;
import io.vertx.mqtt.MqttServer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Mockito.mock;

/**
 * @author Agustin Gambirassi
 **/

@RunWith(SpringRunner.class)
//@SpringBootTest(classes = ShaffiroApp.class)
public class MQTTLBrokerServiceTest {
    private MqttEndpoint endpoint;
    private MQTTBrokerService brokerService;

    @Before
    public void generateMocks() {
        endpoint = mock(MqttEndpoint.class);
        ReglasEngineService reglasEngineService = null;
        brokerService = new MQTTBrokerService(reglasEngineService);
    }

    @Test
    public void mqttBrokerTest() throws Exception {
        brokerService.handler(endpoint);
    }
}
