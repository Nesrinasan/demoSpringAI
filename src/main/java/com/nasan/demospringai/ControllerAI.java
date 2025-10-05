package com.nasan.demospringai;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ControllerAI {
    private final ChatModel chatModel;
    private final ChatClient chatClient;

    public ControllerAI(ChatModel chatModel, ChatClient.Builder chatClient) {
        this.chatModel = chatModel;
        this.chatClient = chatClient.build();
    }

    @GetMapping("/hello")
    public String hello(){
        return chatModel.call("türkiyenin başkenti neresi");
    }


    /**
     * you can use fluent API to generate a response
     * @param userInput
     * @return
     */
    @GetMapping("/ai")
    public String aiGeneration(@RequestParam String userInput){
        return this.chatClient.prompt()
                .user(userInput)
                .call().content();
    }

    /**
     * It includes metadata about how the response was generated and can also contain multiple responses
     * response Doc -> chatResponse.txt
     * @param userInput
     * @return
     */
    @GetMapping("/ai/chatResponse")
    public ChatResponse aiGeneration2(@RequestParam String userInput){
        return this.chatClient.prompt()
                .user(userInput)
                .call().chatResponse();
    }

    /**
     * You can return Entity
     * add I want Films' name is Capital letter. And I Want to see Films'
     * @return Document -> chatResponseEntity.txt
     */
    @GetMapping("/ai/chatResponseEntity")
    public List<ActorFilms> chatResponse3(){
        return this.chatClient.prompt()
                .user("Generate the filmography of 5 movies for Angelina Jolie and Emma Watson.I want Films' name is Capital letter.")
                .call().entity(new ParameterizedTypeReference<List<ActorFilms>>() {});
    }

                 //   .user("Generate the filmography of 5 movies for Angelina Jolie and Emma Watson.I want Films' name is Capital letter. And Give the dates of movies")



    /**
     * You can return Entity
     * add I want Films' name is Capital letter. And I Want to see Films' dates
     * Document -> PNG chatResponseEntityError.png
     * @return
     */
    @GetMapping("/ai/chatResponseEntityError")
    public List<ActorFilms> chatResponseEntity(){
        return this.chatClient.prompt()
                .user("Generate the filmography of 5 movies for Angelina Jolie and Emma Watson.I want Films' name is Capital letter. And Give the dates of movies.  ")
                .call().entity(new ParameterizedTypeReference<List<ActorFilms>>() {});
    }

    /**
     * You can return Entity
     * add I want Films' name is Capital letter. And I Want to see Films' dates
     * Document -> chatResponseEntityWithDateResponse.txt
     * @return
     */
    @GetMapping("/ai/chatResponseEntityWithDateResponse")
    public List<ActorFilms> chatResponseEntity3(){
        return this.chatClient.prompt()
                .user("Generate the filmography of 5 movies for Angelina Jolie and Emma Watson.I want Films' name is Capital letter. And Give the dates of movies near the Film name")
                .call().entity(new ParameterizedTypeReference<List<ActorFilms>>() {});
    }
}
