package com.nasan.demospringai.rag;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.QuestionAnswerAdvisor;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rag")
public class ChatController {
    private static final Logger log = LoggerFactory.getLogger(ChatController.class);

    private final ChatClient chatClient;
    private final IngestionService ingestionService;

    public ChatController(ChatClient.Builder builder, VectorStore vectorStore, IngestionService ingestionService) {

        this.ingestionService = ingestionService;

        this.chatClient = builder
                .defaultAdvisors(new QuestionAnswerAdvisor(vectorStore))
                .build();
    }

    @GetMapping("/ask")
    public String chat(@RequestBody AskJavaPDF askJavaPDF) {
        return chatClient.prompt()
                .user(askJavaPDF.ask())
                .call()
                .content();
    }

    @PostMapping("/add")
    public void add() throws Exception {
        ingestionService.addPdf();
    }
}
