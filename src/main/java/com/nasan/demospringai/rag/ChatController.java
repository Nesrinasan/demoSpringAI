package com.nasan.demospringai.rag;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.QuestionAnswerAdvisor;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.ai.transformer.splitter.TextSplitter;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rag")
public class ChatController {
    private static final Logger log = LoggerFactory.getLogger(ChatController.class);

    private final ChatClient chatClient;
    @Value("classpath:/docs/SSS.pdf")
    private Resource interviewDb;
    private final IngestionService ingestionService;

    public ChatController(ChatClient.Builder builder, VectorStore vectorStore, IngestionService ingestionService) {

        this.ingestionService = ingestionService;

        this.chatClient = builder
                .defaultAdvisors(new QuestionAnswerAdvisor(vectorStore))
                .build();
    }

    @GetMapping("/ask")
    public String chat() {
        return chatClient.prompt()
                .user("sizden ürün aldım ama iade edemiyorum. ne yapabilirim?")
                .call()
                .content();
    }

    @PostMapping("/add")
    public void add() throws Exception {
        ingestionService.addPdf();
    }
}
