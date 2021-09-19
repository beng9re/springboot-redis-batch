package web.controller;

import chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
public class PageController {

    private final ChatService chatService;

    @RequestMapping("/")
    public String main() {
        chatService.chat();
        return "main";
    }

}
