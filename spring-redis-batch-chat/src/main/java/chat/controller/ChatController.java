package chat.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/chat")
public class ChatController {

    @GetMapping("publish")
    public String publish(){
        return "publish";
    }

    @PostMapping("/post")
    public String post(@RequestBody String body){
        return "post";
    }
}
