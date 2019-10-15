package com.shaffiro.service;

import com.shaffiro.domain.DispositivoNoAsociado;
import com.shaffiro.service.dto.DispositivoNoAsociadoDTO;
import com.sun.net.httpserver.HttpServer;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.datagram.DatagramPacket;
import io.vertx.core.datagram.DatagramSocket;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.core.net.NetServer;
import io.vertx.core.net.NetSocket;
import io.vertx.ext.web.RoutingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.HttpRequestHandler;

import javax.annotation.PostConstruct;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Agustin Gambirassi
 **/
@Service
public class DiscoveryService {
    private final Logger log = LoggerFactory.getLogger(DiscoveryService.class);

    @Autowired
    private DispositivoNoAsociadoService dispositivoNoAsociadoService;

    public void handle(RoutingContext routingContext){
        Map<String, JsonObject> request = new HashMap<>();
        JsonObject body = routingContext.getBodyAsJson();
        HttpServerResponse response = routingContext.response();
        log.debug("Receive: " + body.toString());
        DispositivoNoAsociadoDTO dispositivoNoAsociado = new DispositivoNoAsociadoDTO();
        dispositivoNoAsociado.setMac(body.getJsonObject("dispositivo").getString("mac"));
        dispositivoNoAsociado.setUuid(body.getJsonObject("dispositivo").getString("uuid"));
        try{
            dispositivoNoAsociadoService.save(dispositivoNoAsociado);
        }catch (Exception ex){
            ex.printStackTrace();
            log.error("Error al guardar dto");
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
