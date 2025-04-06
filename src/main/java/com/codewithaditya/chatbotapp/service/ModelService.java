package com.codewithaditya.chatbotapp.service;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;
import java.util.List;

@Setter
@Getter
@Service
@ConfigurationProperties(prefix = "langchain4j.ollama")
public class ModelService {

    private List<String> models;

    public List<String> getAvailableModels() {
        return models;
    }
}
