package com.nasan.demospringai.toolcalling;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("toolCalling")
public class ToolCallingController {

    private final OpenAiChatModel chatModel;

    public ToolCallingController( OpenAiChatModel chatModel) {
        this.chatModel = chatModel;
    }



    @RequestMapping("/noToolCalling/currentTime")
    public String noToolCallingCurrentTime(){

        return ChatClient.create(chatModel)
                .prompt("What day is tomorrow?")
                .call()
                .content();

    }


    @RequestMapping("/currentTime")
    public String currentTime(){
        return ChatClient.create(chatModel)
                .prompt("What day is tomorrow?")
                .tools(new DateTimeTools())
                .call()
                .content();

    }



    @RequestMapping("/setAlarm")
    public String toolCallingCurrentTime(){

        return ChatClient.create(chatModel)
                .prompt("Can you set an alarm 10 minutes from now?")
                .tools(new DateTimeTools())
                .call()
                .content();


    }



}
