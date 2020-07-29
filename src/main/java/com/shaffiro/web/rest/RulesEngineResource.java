package com.shaffiro.web.rest;

import com.shaffiro.service.MQTTBrokerService;
import com.shaffiro.service.dto.ProcessValueDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URISyntaxException;

/**
 * @author Agustin Gambirassi
 **/
@RestController
@RequestMapping("/api")
public class RulesEngineResource {

    private final MQTTBrokerService brokerService;

    public RulesEngineResource(MQTTBrokerService brokerService) {
        this.brokerService = brokerService;
    }


    @PostMapping("/receiveAction")
    public void receiveAction(@Valid @RequestBody ProcessValueDTO pvDTO) throws URISyntaxException {
        brokerService.sendMessage(pvDTO);
    }
}
