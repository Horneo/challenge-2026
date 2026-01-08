package com.challenge_2026.punto_de_venta_acc.beans;



import java.time.Duration;

import tools.jackson.databind.json.JsonMapper; // Jackson 3
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJacksonJsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@EnableCaching
public class RedisCacheConfig {

    // Usa el JsonMapper autoconfigurado por Spring Boot (Jackson 3)
    @Bean
    public RedisCacheManager cacheManager(RedisConnectionFactory connectionFactory,
                                          JsonMapper jsonMapper) {

        var valueSerializer = new GenericJacksonJsonRedisSerializer(jsonMapper);

        var cacheConfig = RedisCacheConfiguration
                .defaultCacheConfig()
                .serializeKeysWith(RedisSerializationContext.SerializationPair
                        .fromSerializer(new StringRedisSerializer()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair
                        .fromSerializer(valueSerializer))
                .entryTtl(Duration.ofMinutes(10))
                .disableCachingNullValues();

        return RedisCacheManager.builder(connectionFactory)
                .cacheDefaults(cacheConfig)
                .build();
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory,
                                                       JsonMapper jsonMapper) {
        var template = new RedisTemplate<String, Object>();
        template.setConnectionFactory(connectionFactory);

        var keySer = new StringRedisSerializer();
        var valSer = new GenericJacksonJsonRedisSerializer(jsonMapper);

        template.setKeySerializer(keySer);
        template.setValueSerializer(valSer);
        template.setHashKeySerializer(keySer);
        template.setHashValueSerializer(valSer);

        template.afterPropertiesSet();
        return template;
    }
}


