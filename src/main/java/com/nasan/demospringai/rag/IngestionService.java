package com.nasan.demospringai.rag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.ai.reader.pdf.ParagraphPdfDocumentReader;
import org.springframework.ai.transformer.splitter.TextSplitter;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
@Component
public class IngestionService{

    private static final Logger log = LoggerFactory.getLogger(IngestionService.class);
    private final VectorStore vectorStore;

    @Value("classpath:/docs/SSS.pdf")
    private Resource interviewDb;

    public IngestionService(VectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }

//    @Override
//    public void run(String... args) throws Exception {
//        var pdfReader = new PagePdfDocumentReader(interviewDb);
//        TextSplitter textSplitter = new TokenTextSplitter();
//        vectorStore.accept(textSplitter.apply(pdfReader.get()));
//        log.info("VectorStore Loaded with data!");
//    }


    public void addPdf() throws Exception {
        var pdfReader = new PagePdfDocumentReader(interviewDb);
        TextSplitter textSplitter = new TokenTextSplitter();
        vectorStore.accept(textSplitter.apply(pdfReader.get()));
        log.info("VectorStore Loaded with data!");
    }
}
