package redis;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


@Slf4j
public class ChatMessageSubscriber implements MessageListener {


    Map<String,SseEmitter> sseHash = new HashMap();

    public Map<String,SseEmitter> getChatEmitter() {
        return sseHash;
    }

    //메시지를 받을때 동작하는 로직
    @Override
    public void onMessage(Message message, byte[] pattern) {

        sseHash.forEach((key,sse) -> {
            try {
                sse.send(new String(message.getBody()),MediaType.APPLICATION_JSON);
            }catch (IOException ie){
                log.error(ie.getMessage());
            }
        });

        System.out.println("Message received : " + message.toString());
    }
}
