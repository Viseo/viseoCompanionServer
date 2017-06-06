package com.viseo.companion.controller;

import com.google.gson.Gson;
import com.viseo.companion.converter.CommentConverter;
import com.viseo.companion.dto.ChatMessageDTO;
import com.viseo.companion.dto.CommentDTO;
import com.viseo.companion.dto.JoinRoomDTO;
import com.viseo.companion.dto.LiveActionDTO;
import com.viseo.companion.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class LiveMessageHandler extends TextWebSocketHandler {

    @Autowired
    CommentService commentService;

    Map<Long, List<WebSocketSession>> chatRooms = new HashMap<>();
    List<WebSocketSession> activeSeesions = new ArrayList<>();
    Gson gson = new Gson();
    CommentConverter converter = new CommentConverter();

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        System.out.println("Session close");
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        System.out.println("Session Open !");
        sendGreetingMessage(session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage textMessage) throws Exception {
        LiveActionDTO action = gson.fromJson(textMessage.getPayload(), LiveActionDTO.class);
        switch (action.getType()) {
            case 1:
                joinRoom(session, action);
                break;
            case 2:
                ChatMessageDTO chatMessage = gson.fromJson(action.getPayload(), ChatMessageDTO.class);
                CommentDTO savedMessage = saveChatMessage(chatMessage);
                broadcastMessageToChatRoom(savedMessage, savedMessage.getEventId());
                //todo handle if session is not register in a chatRoom
                break;
            default:
                break;
        }
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception)
            throws Exception {
        session.close(CloseStatus.SERVER_ERROR);
    }

    private void broadcastMessageToChatRoom(CommentDTO message, long chatRoomId) throws Exception {
        String messageJson = gson.toJson(message, CommentDTO.class);
        if (message != null) {
            List<WebSocketSession> chatRoom = chatRooms.get(chatRoomId);
            for (WebSocketSession session : chatRoom) {
                session.sendMessage(new TextMessage(messageJson));
            }
        }
    }

    private List<WebSocketSession> getChatRoom(long eventId) {
        List<WebSocketSession> chatRoom = chatRooms.get(eventId);
        if (chatRoom == null) {
            chatRoom = new ArrayList<>();
            chatRooms.put(eventId, chatRoom);
        }
        return chatRoom;
    }

    private void joinRoom(WebSocketSession session, LiveActionDTO action) {
        JoinRoomDTO joinRoom = gson.fromJson(action.getPayload(), JoinRoomDTO.class);
        List<WebSocketSession> chatRoom = getChatRoom(joinRoom.getEventId());
        chatRoom.add(session);
        sendMessagesSinceLastConnection(session, joinRoom);
    }

    private CommentDTO saveChatMessage(ChatMessageDTO receivedMessage) {
        CommentDTO commentDTO = converter.getDTO(receivedMessage);
        CommentDTO savedComment = commentService.addComment(commentDTO);
        return savedComment;
    }

    private void sendGreetingMessage(WebSocketSession session) {
        CommentDTO message = new CommentDTO();
        message.setContent("Bienvenue sur le live! Vous pouvez poser toutes vos questions ici :)");
        String messageString = gson.toJson(message);
        try {
            session.sendMessage(new TextMessage(messageString));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void sendMessagesSinceLastConnection(WebSocketSession session, JoinRoomDTO joinRoom) {
        try {
            String lastUpdated = String.valueOf(joinRoom.getLastUpdated());
            List<CommentDTO> chatHistory = commentService.getCommentsByEventAfterDate(joinRoom.getEventId(), lastUpdated);
            for (CommentDTO comment : chatHistory) {
                String commentJson = gson.toJson(comment, CommentDTO.class);
                session.sendMessage(new TextMessage(commentJson));
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

}
