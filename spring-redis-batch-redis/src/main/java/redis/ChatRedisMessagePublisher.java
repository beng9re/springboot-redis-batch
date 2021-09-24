package redis;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;

public class ChatRedisMessagePublisher implements MessagePublisher{

    private final RedisTemplate redisTemplate;
    private final ChannelTopic channelTopic;


    public ChatRedisMessagePublisher(RedisTemplate redisTemplate, ChannelTopic channelTopic){
        this.redisTemplate = redisTemplate;
        this.channelTopic  = channelTopic;
    }


    @Override
    public void publish(String message) {
        // 목적지 채널에 메시지를 전달하는 역할
        redisTemplate.convertAndSend(channelTopic.getTopic(),message);

    }
}
