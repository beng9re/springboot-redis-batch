package chat.service;

import chat.domain.Chat;
import chat.domain.ChatPostDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import redis.ChatRedisMessagePublisher;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@ComponentScan("redis")
@Slf4j
public class ChatServiceImpl implements ChatService{

    final private ChatRedisMessagePublisher chatRedisMessagePublisher;
    final private ObjectMapper objectMapper;
    private final RedisTemplate redisTemplate;

    @Override
    public void chat(ChatPostDto dto){
        try {
            Chat chat =  dto.getChatEntity();
            chatRedisMessagePublisher.publish(objectMapper.writeValueAsString(chat));
            ListOperations listOperations = redisTemplate.opsForList();
            listOperations.rightPush("chat",objectMapper.writeValueAsString(chat));
        }catch ( JsonProcessingException e){
            log.error(e.getMessage());
        }
    }
    @Override
    public List<Chat> totalChat() {
        ListOperations listOperations =  redisTemplate.opsForList();
        //Range 함수 데이터 가져오기
        return (List<Chat>) listOperations.range("chat",0,listOperations.size("chat"))
                .stream()
                .map((data)-> {
                    try {
                        return objectMapper.readValue(data.toString(),Chat.class);
                    }catch (Exception e){
                        log.error(e.getMessage());
                    }
                    return null;
                })
                .collect(Collectors.toList());
    }
}
