package chat.controller;

import chat.domain.ChatPostDto;
import chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/chat")
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;


    @GetMapping("publish")
    public String publish(){
        return "publish";
    }

    @PostMapping("/post")
    public String post(@RequestBody ChatPostDto chatPostDto){
        chatService.chat(chatPostDto);
        return "post";
    }
}
