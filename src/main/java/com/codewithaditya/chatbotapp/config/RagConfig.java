package com.codewithaditya.chatbotapp.config;

import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.loader.FileSystemDocumentLoader;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.memory.chat.ChatMemoryProvider;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.ollama.OllamaEmbeddingModel;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.chroma.ChromaEmbeddingStore;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.util.List;
import java.util.Map;

@Configuration
public class RagConfig {

    private static final Logger log = LoggerFactory.getLogger(RagConfig.class);

    @Value("${ai.docs.location}")
    private String docsLocation;

    @Value("${langchain4j.ollama.base-url}")
    private String ollamaBaseUrl;

    @Value("${langchain4j.ollama.models:mistral}")
    private String ollamaModel;

    @Bean
    ChatMemoryProvider chatMemoryProvider() {
        return chatId -> MessageWindowChatMemory.withMaxMessages(10);
    }

    /**
     * Defines Ollama as the embedding model.
     */
    @Bean
    EmbeddingModel embeddingModel() {
        return new OllamaEmbeddingModel(
                ollamaBaseUrl,
                ollamaModel,
                Duration.ofSeconds(30),  // Timeout
                3,  // Max retries
                false,  // Log requests
                false,  // Log responses
                Map.of()  // Custom headers
        );
    }

    /**
     * Defines ChromaDB as the embedding store.
     */

    @Bean
    public EmbeddingStore<TextSegment> embeddingStore() {
        String collectionName = "chat-embeddings"; // Keep it consistent

        try {
            // Attempt to create collection only if it does NOT exist
            return new ChromaEmbeddingStore(
                    "http://localhost:8000",
                    collectionName,
                    Duration.ofSeconds(30),
                    true,
                    true
            );
        } catch (Exception e) {
            System.out.println("⚠️ Collection already exists: " + collectionName);
            return new ChromaEmbeddingStore(
                    "http://localhost:8000",
                    collectionName,
                    Duration.ofSeconds(30),
                    false, // Don't recreate
                    true
            );
        }
    }



    /**
     * Imports documents into the vector store at application startup.
     */
    @Bean
    ApplicationRunner docImporter(EmbeddingStore<TextSegment> embeddingStore, EmbeddingModel embeddingModel) {
        return args -> {
            if (docsLocation == null || docsLocation.isEmpty()) {
                log.error("ERROR!!");
                log.error("No document location specified. Configure 'ai.docs.location' in application.yml");
                return;
            }
            log.info("Importing documents from {}", docsLocation);
            List<Document> docs = FileSystemDocumentLoader.loadDocuments(docsLocation);

            EmbeddingStoreIngestor.ingest(docs, embeddingStore);

            log.info("Imported {} documents into ChromaDB", docs.size());
        };
    }

    /**
     * Retrieves relevant content from ChromaDB for AI responses.
     */
    @Bean
    ContentRetriever contentRetriever(EmbeddingStore<TextSegment> embeddingStore) {
        return EmbeddingStoreContentRetriever.from(embeddingStore);
    }
}
