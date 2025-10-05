package com.nasan.demospringai;
import org.springframework.ai.chat.model.Generation;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.ai.converter.ListOutputConverter;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.core.ParameterizedTypeReference;


import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/")
public class StructuredController {

    private final OpenAiChatModel chatModel;
    public StructuredController(OpenAiChatModel chatModel) {
        this.chatModel = chatModel;
    }

    //and film date as date and director of film as director
    @GetMapping("/by-author")
    public Author hello(@RequestParam(defaultValue = "Robert C. Martin") String author){
        String template = """
                Generate  a list of books written by {author}. you should get the name as name, books as books and link as link created date as date .
                   {format}
                """;

        BeanOutputConverter<Author> beanOutputConverter =  new BeanOutputConverter<>(Author.class);
        String format = beanOutputConverter.getFormat();
        PromptTemplate promptTemplate =  new PromptTemplate(template, Map.of("author", author, "format", format));
        Generation generation = chatModel.call(promptTemplate.create()).getResult();
        return beanOutputConverter.convert(generation.getOutput().getText());
    }

    //       String template = """
    //                Generate  a list of books written by {author}. you should get the name as name, books as books and link as link and created date as date  .
    //                   {format}
    //                """;

    @GetMapping("/actor")
    public ActorFilms actorFilms(){
        String template = """
                Generate the filmography of 5 movies for Angelina Jolie . 
                   {format}
                """;

        BeanOutputConverter<ActorFilms> beanOutputConverter =  new BeanOutputConverter<>(ActorFilms.class);
        String format = beanOutputConverter.getFormat();
        PromptTemplate promptTemplate =  new PromptTemplate(template, Map.of( "format", format));
        Generation generation = chatModel.call(promptTemplate.create()).getResult();
        return beanOutputConverter.convert(generation.getOutput().getText());

    }

    @GetMapping("/actorList")
    public List<ActorFilms> actorsFilms(){
        String template = """
                Generate the filmography of 5 movies for Angelina Jolie and Emma watson. 
                   {format}
                """;
        BeanOutputConverter<List<ActorFilms>> beanOutputConverter = new BeanOutputConverter<>(new ParameterizedTypeReference<List<ActorFilms>>() { });
        String format = beanOutputConverter.getFormat();
        PromptTemplate promptTemplate =  new PromptTemplate(template, Map.of( "format", format));
        Generation generation = chatModel.call(promptTemplate.create()).getResult();
        return beanOutputConverter.convert(generation.getOutput().getText());


    }
}
