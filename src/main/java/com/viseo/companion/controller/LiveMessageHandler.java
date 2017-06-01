package com.viseo.companion.controller;

import com.google.gson.Gson;
import com.viseo.companion.dto.CommentDTO;
import com.viseo.companion.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.ArrayList;
import java.util.List;

public class LiveMessageHandler extends TextWebSocketHandler {

    @Autowired
    CommentService commentService;

    List<WebSocketSession> activeSeesions = new ArrayList<>();
    Gson gson = new Gson();

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        System.out.println("Session close");
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        System.out.println("Session Open !");
        activeSeesions.add(session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage textMessage) throws Exception {
        System.out.println("Message received: " + textMessage.getPayload());
        String savedMessage = saveMessage(textMessage.getPayload());
        broadcastMessage(savedMessage);
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception)
            throws Exception {
        session.close(CloseStatus.SERVER_ERROR);
    }

    private String saveMessage(String receivedMessage) {
        CommentDTO commentDTO = gson.fromJson(receivedMessage, CommentDTO.class);
        CommentDTO savedComment = commentService.addComment(commentDTO);
        List<CommentDTO> listAllComments = commentService.getCommentsByEvent(5);
        return gson.toJson(listAllComments);
    }

    private void broadcastMessage(String message) throws Exception {
        if (message != null) {
            for (WebSocketSession activeSession : activeSeesions) {
                activeSession.sendMessage(new TextMessage(message));
            }
        }
    }
}
