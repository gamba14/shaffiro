package com.shaffiro.service;

import com.shaffiro.service.dto.DispositivoNoAsociadoDTO;
import io.vertx.core.datagram.DatagramPacket;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Agustin Gambirassi
 **/
@Service
public class DiscoveryService {
    private final Logger log = LoggerFactory.getLogger(DiscoveryService.class);

    private final DispositivoNoAsociadoService dispositivoNoAsociadoService;

    private List<DispositivoNoAsociadoDTO> dispositivoNoAsociadoList;

    public DiscoveryService(DispositivoNoAsociadoService dispositivoNoAsociadoService) {
        this.dispositivoNoAsociadoService = dispositivoNoAsociadoService;
    }

    public void handle(RoutingContext routingContext){
        dispositivoNoAsociadoList = dispositivoNoAsociadoService.findAll();
        Map<String, JsonObject> request = new HashMap<>();
        JsonObject body = routingContext.getBodyAsJson();
        HttpServerResponse response = routingContext.response();
        log.debug("Receive: " + body.toString());
        DispositivoNoAsociadoDTO dispositivoNoAsociado = new DispositivoNoAsociadoDTO();
        dispositivoNoAsociado.setMac(body.getJsonObject("dispositivo").getString("mac"));
        dispositivoNoAsociado.setUuid(body.getJsonObject("dispositivo").getString("uuid"));
        dispositivoNoAsociado.setPuerto(body.getJsonObject("dispositivo").getInteger("puerto"));
        boolean exists = dispositivoNoAsociadoList.stream()
            .anyMatch(disp -> disp.getMac().equals(body.getJsonObject("dispositivo").getString("mac")));
        try{
            if(!exists) {
                dispositivoNoAsociadoService.save(dispositivoNoAsociado);
            }
        }catch (Exception ex){
//            ex.printStackTrace();
//            log.error("Error al guardar dto");
            this.sendError(500, response);
        }
        response.end();
    }

    public void handleUDP(DatagramPacket socket){
        String request = new String(socket.data().getBytes());
        log.debug("Recibo del socket: " + request);
        JsonObject body = new JsonObject(request);
        log.debug(body.toString());
        DispositivoNoAsociadoDTO dispositivoNoAsociado = new DispositivoNoAsociadoDTO();
        dispositivoNoAsociado.setMac(body.getJsonObject("dispositivo").getString("mac"));
        dispositivoNoAsociado.setUuid(body.getJsonObject("dispositivo").getString("uuid"));
        String ip = socket.sender().host();
        log.debug("IP: " +ip);
        //dispositivoNoAsociado.setUuid(body.getJsonObject("dispositivo").getString("uuid"));
        dispositivoNoAsociado.setUuid(ip);
        try{
            dispositivoNoAsociadoService.save(dispositivoNoAsociado);
        }catch (Exception ex){
            ex.printStackTrace();
            log.error("Error al guardar dto");
        }
    }

    private void sendError(int statusCode, HttpServerResponse response) {
        response.setStatusCode(statusCode).end();
    }

}
