package com.viseo.companion.controller;

import com.google.gson.Gson;
import com.viseo.companion.converter.ChatMessageConverter;
import com.viseo.companion.dto.ChatMessageDTO;
import com.viseo.companion.dto.CommentDTO;
import com.viseo.companion.dto.JoinRoomDTO;
import com.viseo.companion.dto.LiveActionDTO;
import com.viseo.companion.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.*;

public class LiveMessageHandler extends TextWebSocketHandler {

    @Autowired
    CommentService commentService;

    Map<Long, List<WebSocketSession>> chatRooms = new HashMap<>();
    Map<WebSocketSession, Long> sessionChatRoom = new HashMap<>();
    Gson gson = new Gson();
    ChatMessageConverter chatMessageConverter = new ChatMessageConverter();

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        System.out.println("Session close");
        Long chatRoomId = sessionChatRoom.get(session);
        List<WebSocketSession> chatRoom = chatRooms.get(chatRoomId);
        chatRoom.remove(session);
        broadcastParticipantsNumberToChatRoom(chatRoom, chatRoomId);
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        System.out.println("Session Open !");
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage textMessage) {
        LiveActionDTO action = gson.fromJson(textMessage.getPayload(), LiveActionDTO.class);
        switch (action.getType()) {
            case 1:
                joinRoom(session, action);
                break;
            case 2:
                handleReceivedMessage(session, action.getPayload());
                break;
            default:
                break;
        }
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception)
            throws Exception {
        exception.printStackTrace();
        session.close(CloseStatus.SERVER_ERROR);
    }

    private boolean sessionJoined(WebSocketSession session, long eventId) {
        List<WebSocketSession> chatRoom = chatRooms.get(eventId);
        return chatRoom != null && chatRoom.contains(session);
    }

    private void handleReceivedMessage(WebSocketSession session, Object message) {
        try {
            ChatMessageDTO chatMessage = gson.fromJson(message.toString(), ChatMessageDTO.class);
            if (sessionJoined(session, chatMessage.getEventId())) {
                ChatMessageDTO savedMessage = saveChatMessage(chatMessage);
                broadcastChatMessageToChatRoom(savedMessage, savedMessage.getEventId());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    private void broadcastChatMessageToChatRoom(ChatMessageDTO message, long chatRoomId) throws Exception {
        LiveActionDTO liveActionDTO = new LiveActionDTO(2, message);
        broadcastLiveActionToChatRoom(liveActionDTO, chatRoomId);
    }

    private void broadcastLiveActionToChatRoom(LiveActionDTO liveAction, long chatRoomId) throws Exception {
        String liveActionJson = gson.toJson(liveAction, LiveActionDTO.class);
        if (liveActionJson != null) {
            List<WebSocketSession> chatRoom = chatRooms.get(chatRoomId);
            Iterator<WebSocketSession> sessionIterator = chatRoom.iterator();
            while (sessionIterator.hasNext()) {
                WebSocketSession session = sessionIterator.next();
                if (session.isOpen()) {
                    session.sendMessage(new TextMessage(liveActionJson));
                } else {
                    sessionIterator.remove();
                }
            }
        }
    }

    private List<WebSocketSession> getChatRoom(long eventId) {
        return chatRooms.computeIfAbsent(eventId, k -> new ArrayList<>());
    }

    private void joinRoom(WebSocketSession session, LiveActionDTO action) {
        try {
            JoinRoomDTO joinRoom = gson.fromJson(action.getPayload().toString(), JoinRoomDTO.class);
            long eventId = joinRoom.getEventId();
            sessionChatRoom.put(session, eventId);
            List<WebSocketSession> chatRoom = getChatRoom(eventId);
            chatRoom.add(session);
            sendMessagesSinceLastConnection(session, joinRoom);
            broadcastParticipantsNumberToChatRoom(chatRoom, eventId);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    private void broadcastParticipantsNumberToChatRoom(List<WebSocketSession> chatRoom, long chatRoomId) throws Exception {
        int numberOfParticipants = chatRoom.size();
        LiveActionDTO updateNumberParticipants = new LiveActionDTO(3, String.valueOf(numberOfParticipants));
        broadcastLiveActionToChatRoom(updateNumberParticipants, chatRoomId);
    }

    private ChatMessageDTO saveChatMessage(ChatMessageDTO receivedMessage) {
        CommentDTO comment = new CommentDTO();
        chatMessageConverter.apply(receivedMessage, comment);
        CommentDTO savedComment = commentService.addComment(comment);
        return chatMessageConverter.getDTO(savedComment);
    }

    private void sendMessagesSinceLastConnection(WebSocketSession session, JoinRoomDTO joinRoom) {
        try {
            String lastUpdated = String.valueOf(joinRoom.getLastUpdated());
            List<CommentDTO> chatHistory = commentService.getCommentsByEventAfterDate(joinRoom.getEventId(), lastUpdated);
            for (CommentDTO comment : chatHistory) {
                ChatMessageDTO chatMessageDTO = chatMessageConverter.getDTO(comment);
                LiveActionDTO liveActionDTO = new LiveActionDTO(2, chatMessageDTO);
                String liveActionJson = gson.toJson(liveActionDTO, LiveActionDTO.class);
                session.sendMessage(new TextMessage(liveActionJson));
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

}
