package com.codewithaditya.chatbotapp.config;

import dev.langchain4j.model.ollama.OllamaChatModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OllamaConfig {

    @Value("${langchain4j.ollama.base-url}")
    private String baseUrl;

    @Value("${langchain4j.ollama.model}")
    private String modelName;

    @Bean
    public OllamaChatModel ollamaChatModel() {
        return OllamaChatModel.builder()
                .baseUrl(baseUrl)  // Use the injected baseUrl
                .modelName(modelName) //use the injected modelName
                .build();
    }
}
