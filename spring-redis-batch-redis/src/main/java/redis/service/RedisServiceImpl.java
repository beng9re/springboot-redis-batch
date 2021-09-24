package redis.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RedisServiceImpl implements RedisService{
   private final RedisTemplate redisTemplate;

   public void sendMessage(String message){
      ListOperations listOperations = redisTemplate.opsForList();
      listOperations.rightPush("message",message);
   }
}
