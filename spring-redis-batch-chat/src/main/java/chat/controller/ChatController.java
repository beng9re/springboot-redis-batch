package chat.controller;

import chat.domain.ChatPostDto;
import chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import redis.ChatMessageSubscriber;

import java.util.HashMap;

@RestController
@RequestMapping("/chat")
@RequiredArgsConstructor
@Slf4j
public class ChatController {

    private final ChatService chatService;

    private final ChatMessageSubscriber chatMessageSubscriber;


    @GetMapping(path = "/sub" , produces= MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter chatSubscriber(){
        SseEmitter chatEmitter = new SseEmitter(Long.MAX_VALUE);

        String key = Integer.toString(chatEmitter.hashCode());
        HashMap<String,SseEmitter> sseHm = (HashMap<String, SseEmitter>) chatMessageSubscriber.getChatEmitter();
        sseHm.put(key,chatEmitter);
        chatEmitter.onTimeout(()->sseHm.remove(key));
        chatEmitter.onError((e)-> {
            log.error(e.getMessage());
            sseHm.remove(key);
        });
        chatEmitter.onCompletion(()-> sseHm.remove(key));
        return chatEmitter;
    }



    @PostMapping("/post")
    public String post(@RequestBody ChatPostDto chatPostDto){
        chatService.chat(chatPostDto);
        return "post";
    }
}
