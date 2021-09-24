package chat.controller;

import chat.domain.ChatPostDto;
import chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import redis.ChatMessageSubscriber;

import java.io.IOException;
import java.util.HashMap;

import static java.lang.Thread.sleep;

@RestController
@RequestMapping("/chat")
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    private final ChatMessageSubscriber chatMessageSubscriber;


    @GetMapping(path = "/sub" , produces= MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter chatSubscriber(){
        SseEmitter chatEmitter = new SseEmitter(Long.MAX_VALUE);

        String key = Integer.toString(chatEmitter.hashCode());
        HashMap<String,SseEmitter> sseHm = (HashMap<String, SseEmitter>) chatMessageSubscriber.getChatEmitter();
        sseHm.put(key,chatEmitter);

        chatEmitter.onCompletion(()-> sseHm.remove(key));
        return chatEmitter;
    }


    @GetMapping(path = "/test")
    public SseEmitter test() {
        SseEmitter sseEmitter = new SseEmitter();
        Thread th = new Thread(()->{
            try {
                Thread.sleep(1000);
                sseEmitter.send("test");
            }catch (Exception e){

            }
        });

        th.start();

        return sseEmitter;
    }



    @PostMapping("/post")
    public String post(@RequestBody ChatPostDto chatPostDto){
        chatService.chat(chatPostDto);
        return "post";
    }
}
