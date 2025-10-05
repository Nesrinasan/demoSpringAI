package com.nasan.demospringai.toolcalling;

import com.nasan.demospringai.Author;
import com.nasan.demospringai.toolcalling.entity.EmployeeRepository;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.Generation;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

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

        String system = """
    Always produce only JSON in the exact schema:
    {"scheduled":[{"employeeName": "...","alarmTimeIso":"..."}],
     "notScheduled":["..."]}
    No extra text.
    """;
        String user = """
    Çalışan listesinden yaşı 30'dan büyük olanlar için 60 dakika sonrasına alarm kur.
    JSON olarak ver: scheduled (isim+iso saat), notScheduled (isim listesi).
    """;
        return ChatClient.create(chatModel)
                .prompt()
                .system(system)
                .user(user)
                .tools(new EmployeeTools(employeeRepository), new DateTimeTools())
                .call()
                .content();
    }


    @GetMapping("/structured")
    public String getEmployeeStructured() {

        String user = """
    Çalışan listesinden yaşı 30'dan büyük olanlar için 60 dakika sonrasına alarm kur.
    {format}.
    """;

        BeanOutputConverter<AlarmPlan> beanOutputConverter =  new BeanOutputConverter<>(AlarmPlan.class);
        String format = beanOutputConverter.getFormat();
        PromptTemplate promptTemplate =  new PromptTemplate(user, Map.of( "format", format));

        return ChatClient.create(chatModel)
                .prompt(promptTemplate.create())
                .tools(new EmployeeTools(employeeRepository), new DateTimeTools())
                .call()
                .content();
    }
}
