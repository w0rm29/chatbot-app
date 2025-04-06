package com.codewithaditya.chatbotapp.config;

import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.TextMessage;
import org.springframework.stereotype.Component;
import com.codewithaditya.chatbotapp.service.ChatBotService;
import org.springframework.beans.factory.annotation.Autowired;

@Component
public class ChatWebSocketHandler extends TextWebSocketHandler {

    private final ChatBotService chatBotService;

    @Autowired
    public ChatWebSocketHandler(ChatBotService chatBotService) {
        this.chatBotService = chatBotService;
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String userMessage = message.getPayload();
        String botResponse = chatBotService.getChatbotResponse(userMessage);

        session.sendMessage(new TextMessage(botResponse));
    }
}
