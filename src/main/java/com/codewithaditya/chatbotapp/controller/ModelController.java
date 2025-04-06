package com.codewithaditya.chatbotapp.controller;

import com.codewithaditya.chatbotapp.service.ModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/models")
@CrossOrigin("*")
public class ModelController {

    private final ModelService modelService;

    @Autowired
    public ModelController(ModelService modelService) {
        this.modelService = modelService;
    }

    @GetMapping("/list")
    public Map<String, List<String>> listAvailableModels() {
        return Map.of("available_models", modelService.getAvailableModels());
    }
}
