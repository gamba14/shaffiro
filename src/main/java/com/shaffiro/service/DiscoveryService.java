package com.shaffiro.service;

import com.shaffiro.service.dto.DispositivoNoAsociadoDTO;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.datagram.DatagramPacket;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonObject;
import io.vertx.core.net.SocketAddress;
import io.vertx.core.streams.WriteStream;
import io.vertx.ext.web.RoutingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.Charset;
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
            .anyMatch(disp -> disp.getMac().equals(body.getJsonObject("dispositivo").getString("uuid")));
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
        dispositivoNoAsociado.setPuerto(body.getJsonObject("dispositivo").getInteger("puerto"));
        String ip = socket.sender().host();
        log.debug("IP: " +ip);
        //dispositivoNoAsociado.setUuid(body.getJsonObject("dispositivo").getString("uuid"));
        dispositivoNoAsociado.setUuid(ip);
        boolean exists = dispositivoNoAsociadoList.stream()
            .anyMatch(disp -> disp.getMac().equals(body.getJsonObject("dispositivo").getString("uuid")));
        try{
            if(!exists) dispositivoNoAsociadoService.save(dispositivoNoAsociado);
        }catch (Exception ex){
            ex.printStackTrace();
            log.error("Error al guardar dto");
        }
    }

    public void sendConfig(String config, String ip, Integer puerto){
        try {
            byte[] buff = config.getBytes(Charset.defaultCharset());
            log.debug("IP TO SEND CONFIG: {}", ip);
            InetAddress address = InetAddress.getByName(ip);
            DatagramSocket socket = new DatagramSocket(puerto);
            java.net.DatagramPacket packet = new java.net.DatagramPacket(buff, buff.length,address,puerto);
            socket.send(packet);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void sendError(int statusCode, HttpServerResponse response) {
        response.setStatusCode(statusCode).end();
    }

}
