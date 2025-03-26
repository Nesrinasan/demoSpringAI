package com.nasan.demospringai;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.Generation;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Map;

@RestController
@RequestMapping("/books")
public class StructuredController {

    private final OpenAiChatModel chatModel;

    public StructuredController(OpenAiChatModel chatModel) {
        this.chatModel = chatModel;
    }

    //and film date as date and director of film as director
    @GetMapping("/by-author")
    public Author hello(@RequestParam(defaultValue = "Robert C. Martin") String author){
        String template = """
                Generate  a list of books written by {author}. you should get the name as name, books as books and link as link and film date as date and director of film as director .
                   {format}
                """;

        BeanOutputConverter<Author> beanOutputConverter =  new BeanOutputConverter<>(Author.class);
        String format = beanOutputConverter.getFormat();
        PromptTemplate promptTemplate =  new PromptTemplate(template, Map.of("author", author, "format", format));
        Generation generation = chatModel.call(promptTemplate.create()).getResult();
        return beanOutputConverter.convert(generation.getOutput().getText());
    }
}
