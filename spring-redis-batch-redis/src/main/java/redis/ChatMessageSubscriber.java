package redis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


@Slf4j
public class ChatMessageSubscriber implements MessageListener {

    //각 클라이언트에 맵핑된 SseEmitter
    Map<String,SseEmitter> sseHash = new HashMap();

    public Map<String,SseEmitter> getChatEmitter() {
        return sseHash;
    }

    //메시지를 받을때 동작하는 로직
    @Override
    public void onMessage(Message message, byte[] pattern) {

        sseHash.forEach((key,sse) -> {
            try {
                //message 객체를 json 형식으로 반환
                sse.send(new String(message.getBody()),MediaType.APPLICATION_JSON);
            }catch (IOException ie){
                log.error(ie.getMessage());
            }
        });

        System.out.println("Message received : " + message.toString());
    }
}
