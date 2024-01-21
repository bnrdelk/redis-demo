package redisDemo3;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
public class Publisher {

    @Autowired
    @Qualifier("redisTemplate")  // for specific RedisTemplate bean
    private RedisTemplate<String, Object> template;

    @Autowired
    private ChannelTopic topic;

    @PostMapping("/publish")
    public String publish(@RequestBody Person person) {
        template.convertAndSend(topic.getTopic(), person.toString());
        return "Published successfully.";
    }
}

