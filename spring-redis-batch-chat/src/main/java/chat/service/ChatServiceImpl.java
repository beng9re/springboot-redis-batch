package chat.service;

import chat.domain.ChatPostDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ComponentScan;

import org.springframework.stereotype.Service;
import redis.ChatRedisMessagePublisher;
import redis.service.RedisService;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;


@Service
@RequiredArgsConstructor
@ComponentScan("redis")
@Slf4j
public class ChatServiceImpl implements ChatService{

    final private ChatRedisMessagePublisher chatRedisMessagePublisher;
    final private ObjectMapper objectMapper;

    public void chat(ChatPostDto dto){
        try {
            chatRedisMessagePublisher.publish(objectMapper.writeValueAsString(dto));
        }catch ( JsonProcessingException e){
            log.error(e.getMessage());
        }

    }

}
