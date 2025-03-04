package com.codewithaditya.chatbotapp.repository;

import com.codewithaditya.chatbotapp.model.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
}
