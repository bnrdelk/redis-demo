package redisDemo2;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.Jedis;

import java.time.Duration;

@Configuration
public class RedisConfig {

    // get values from properties
    @Value(("${spring.redis.host}"))
    private String redisHost;

    @Value(("${spring.redis.port}"))
    private int redisPort;

    @Bean
    JedisConnectionFactory jedisConnectionFactory() {

        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        // set default values
        redisStandaloneConfiguration.setHostName(redisHost);
        redisStandaloneConfiguration.setPort(redisPort);
        redisStandaloneConfiguration.setDatabase(0);

        // create a pool for connections
        GenericObjectPoolConfig<Jedis> genericObjectPoolConfig = new GenericObjectPoolConfig<Jedis>();
        genericObjectPoolConfig.setMaxTotal(10); // max 10 connections
        genericObjectPoolConfig.setMaxIdle(5);   // max 5 idle when it is not using that much connections -> for performance
        genericObjectPoolConfig.setMinIdle(2);   // min 2 connections ready in pool

        JedisClientConfiguration.JedisClientConfigurationBuilder jedisClientConfiguration = JedisClientConfiguration.builder();
        jedisClientConfiguration.connectTimeout(Duration.ofMinutes(1)); // timeout extended for creation/deletion op's of connections
        jedisClientConfiguration.usePooling().poolConfig(genericObjectPoolConfig);

        return new JedisConnectionFactory(redisStandaloneConfiguration,
                jedisClientConfiguration.build());
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        return template;
    }

}
