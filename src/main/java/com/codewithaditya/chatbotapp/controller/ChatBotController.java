package com.codewithaditya.chatbotapp.controller;

import com.codewithaditya.chatbotapp.service.ChatBotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/chat")
@CrossOrigin("*")
public class ChatBotController {

    @Autowired
    private ChatBotService chatService;

    @PostMapping("/ask")
    public Map<String, String> askChatbot(@RequestBody Map<String, String> request) {
        String userMessage = request.get("message");
        String response = chatService.getChatbotResponse(userMessage);
        return Map.of("response", response);
    }
}
