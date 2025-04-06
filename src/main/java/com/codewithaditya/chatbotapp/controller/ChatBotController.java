package com.codewithaditya.chatbotapp.controller;

import com.codewithaditya.chatbotapp.service.ChatBotService;
import dev.langchain4j.data.document.Metadata;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.data.embedding.Embedding;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/chat")
@CrossOrigin("*")
public class ChatBotController {

    private final ChatBotService chatBotService;
    private final EmbeddingStore<TextSegment> embeddingStore;
    private final EmbeddingModel embeddingModel;

    @Autowired
    public ChatBotController(ChatBotService chatBotService, EmbeddingStore<TextSegment> embeddingStore, EmbeddingModel embeddingModel) {
        this.chatBotService = chatBotService;
        this.embeddingStore = embeddingStore;
        this.embeddingModel = embeddingModel;
    }

    @PostMapping("/ask")
    public Map<String, String> askChatbot(@RequestBody Map<String, String> request) {
        String userMessage = request.get("message");
        String response = chatBotService.getChatbotResponse(userMessage);
        return Map.of("response", response);
    }

    @PostMapping("/ingest")
    public String ingestDocument(@RequestBody Map<String, String> request) {
        String text = request.get("text");
        String id = UUID.randomUUID().toString();

        // ✅ Create metadata (Optional)
        Metadata metadata = Metadata.from(Map.of("id", id, "source", "API"));

        // ✅ Create TextSegment with metadata
        TextSegment textSegment = new TextSegment(text, metadata);

        // ✅ Generate embedding for the text
        Embedding embedding = embeddingModel.embed(textSegment.text()).content();

        // ✅ Store the embedding with the segment
        embeddingStore.add(embedding);

        return "Document added successfully!";
    }
}
