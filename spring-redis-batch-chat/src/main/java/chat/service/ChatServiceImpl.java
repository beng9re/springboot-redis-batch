package chat.service;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.stereotype.Service;
import redis.service.RedisService;
import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
@ComponentScan("redis")
public class ChatServiceImpl implements ChatService{

    final private RedisService redisService;

    public void chat(){
        redisService.test();
    }

}
