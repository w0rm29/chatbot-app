package com.codewithaditya.chatbotapp.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "chat_messages")
public class ChatMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String userMessage;

    @Column(columnDefinition = "TEXT")  // Ensure large responses fit
    private String botResponse;

    @Column(name = "timestamp")
    private String timestamp;
}
