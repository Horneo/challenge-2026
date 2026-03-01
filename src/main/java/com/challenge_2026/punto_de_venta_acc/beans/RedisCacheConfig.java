package com.challenge_2026.punto_de_venta_acc.beans;



import com.challenge_2026.punto_de_venta_acc.dto.MinimumGraphPOSDto;
import com.challenge_2026.punto_de_venta_acc.dto.POSDto;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.serializer.*;


import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class RedisCacheConfig {

    /*@SuppressWarnings({ "deprecation", "removal"})
    public static RedisSerializer<Object> genericJackson(ObjectMapper objectMapper) {
        ObjectMapper om = objectMapper.copy();
        om.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);
        // Register known DTOs so Jackson can resolve polymorphic types stored without explicit type id
        om.registerSubtypes(POSDto.class, MinimumGraphPOSDto.class);
        // Be lenient on unknown properties to avoid deserialization failures from older payload shapes
        om.configure(com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return new GenericJackson2JsonRedisSerializer(om);
    }*/

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    /*@Bean
    public RedisCacheManager redisCacheManager(RedisConnectionFactory connectionFactory,
                                               ObjectMapper objectMapper) {

        ObjectMapper om = objectMapper.copy();
        om.activateDefaultTyping(
                LaissezFaireSubTypeValidator.instance,
                ObjectMapper.DefaultTyping.NON_FINAL,
                JsonTypeInfo.As.PROPERTY
        );
        om.registerSubtypes(POSDto.class, MinimumGraphPOSDto.class);
        om.configure(com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        RedisSerializer<Object> json = genericJackson(om);

        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(json));

        return RedisCacheManager.builder(connectionFactory)
                .cacheDefaults(config)
                .build();
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory,
                                                       ObjectMapper objectMapper) {
        var template = new RedisTemplate<String, Object>();
        template.setConnectionFactory(connectionFactory);

        var keySer = new StringRedisSerializer();
        var valSer = genericJackson(objectMapper);

        template.setKeySerializer(keySer);
        template.setValueSerializer(valSer);
        template.setHashKeySerializer(keySer);
        template.setHashValueSerializer(valSer);

        template.afterPropertiesSet();
        return template;
    }*/
}


