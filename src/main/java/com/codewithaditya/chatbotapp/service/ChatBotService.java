package com.codewithaditya.chatbotapp.service;

import dev.langchain4j.model.ollama.OllamaChatModel;
import com.codewithaditya.chatbotapp.model.ChatMessage;
import com.codewithaditya.chatbotapp.repository.ChatMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatBotService {

    private final OllamaChatModel chatModel;
    private final ChatMessageRepository chatMessageRepository;

    @Autowired
    public ChatBotService(OllamaChatModel chatModel, ChatMessageRepository chatMessageRepository) {
        this.chatModel = chatModel;
        this.chatMessageRepository = chatMessageRepository;
    }

    public String getChatbotResponse(String userMessage) {
        String response = chatModel.generate(userMessage);

        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setUserMessage(userMessage);
        chatMessage.setBotResponse(response);
        chatMessageRepository.save(chatMessage);

        return response;
    }
}
