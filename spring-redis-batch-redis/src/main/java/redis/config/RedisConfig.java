package redis.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.ChatMessageSubscriber;
import redis.ChatRedisMessagePublisher;

@Configuration
@EnableRedisRepositories
public class RedisConfig {
    @Value("${spring.redis.host}")
    private String redisHost;

    @Value("${spring.redis.port}")
    private int redisPort;

    private ChatMessageSubscriber chatMessageSubscriber = new ChatMessageSubscriber();

    @Bean
    MessageListenerAdapter messageListener(){
        return new MessageListenerAdapter();

    }
    //레디스 컨테이너 생성
    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        return new LettuceConnectionFactory(redisHost, redisPort);
    }


    @Bean
    public ChatRedisMessagePublisher chatPublisher(){
        return new ChatRedisMessagePublisher(redisTemplate(),chatTopic());
    }


    //토픽생성
    @Bean(name = "chatTopic")
    ChannelTopic chatTopic(){
        return new ChannelTopic("chatQue");
    }

    @Bean
    ChatMessageSubscriber chatMessageSubscriber(){
        return chatMessageSubscriber;
    }

    @Bean
    public MessageListenerAdapter chatMessageListener(){
        return new MessageListenerAdapter(chatMessageSubscriber);
    }


    @Bean
    RedisMessageListenerContainer redisContainer(){
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(redisConnectionFactory());
        //체팅 메시지 등록
        container.addMessageListener(chatMessageListener(),chatTopic());

        return container;
    }


    //레디스 템플릿 빈설정
    @Bean
    public RedisTemplate<?, ?> redisTemplate() {
        RedisTemplate<byte[], byte[]> redisTemplate = new RedisTemplate<>();
        //시리얼라이저 (인코딩 처리)
        redisTemplate.setDefaultSerializer(new StringRedisSerializer());
        redisTemplate.setConnectionFactory(redisConnectionFactory());

        return redisTemplate;
    }
}
