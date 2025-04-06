package com.codewithaditya.chatbotapp.service;

import dev.langchain4j.model.StreamingResponseHandler;
import dev.langchain4j.model.ollama.OllamaChatModel;
import com.codewithaditya.chatbotapp.model.ChatMessage;
import com.codewithaditya.chatbotapp.repository.ChatMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
        // Check if the response exists in the database
        Optional<ChatMessage> cachedMessage = chatMessageRepository.findByUserMessage(userMessage);

        if (cachedMessage.isPresent()) {
            return cachedMessage.get().getBotResponse();  // ✅ Return cached response
        }

        // If not found, generate response from Llama
        String response = chatModel.generate(userMessage);

        // Save new response in DB for future use
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setUserMessage(userMessage);
        chatMessage.setBotResponse(response);
        chatMessageRepository.save(chatMessage);

        return response;
    }
}
