package com.chat.client.web;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.converter.StringMessageConverter;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import java.io.Reader;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("ws")
@RequiredArgsConstructor
public class StreamController {

    private StompSession session;
    private final WebSocketStompClient client;
//    private final SimpMessagingTemplate template;

    @GetMapping("connect")
    public void connect(@RequestParam("message") String message) throws ExecutionException, InterruptedException {
        ListenableFuture<StompSession> connect = client.connect("ws://localhost:8080/connect", new SessionHandler());
        this.session = connect.get();
    }

    @GetMapping("send")
    public void sendMessage(@RequestParam("message") String message) {
//        template.convertAndSend("ws://localhost:8080/app/connect", message);
        session.send("/app/connect", message);
    }

}
