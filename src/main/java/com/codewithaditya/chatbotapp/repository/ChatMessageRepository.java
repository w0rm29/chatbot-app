package com.codewithaditya.chatbotapp.repository;

import com.codewithaditya.chatbotapp.model.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    Optional<ChatMessage> findByUserMessage(String userMessage);
}

