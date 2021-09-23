package web.controller;

import chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller

public class PageController {



    @RequestMapping("/")
    public String main() {
        return "main";
    }


}
