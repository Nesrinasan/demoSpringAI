package com.nasan.demospringai.toolcalling;

import com.nasan.demospringai.toolcalling.entity.EmployeeRepository;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/employee")
public class EmployeeController {


    @Autowired
    EmployeeRepository employeeRepository;
    private final OpenAiChatModel chatModel;

    public EmployeeController( OpenAiChatModel chatModel) {
        this.chatModel = chatModel;
    }

    @GetMapping()
    public String getEmployee() {

        return ChatClient.create(chatModel)
                .prompt("Retrieve employee information for employee with id 2 and if he/seh is avaiable return summary otherwise return just null mesaage")
                .tools(new EmployeeTools(employeeRepository))
                .call()
                .content();
    }

}
