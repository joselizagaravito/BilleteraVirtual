package com.grupo08.yanki.config;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;

@Configuration
public class CacheConfig extends CachingConfigurerSupport{
	
	public static final String USER_CACHE = "wallet-cache";

    @Bean
    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
        Map<String, RedisCacheConfiguration> redisCacheConfigurationMap = new HashMap();
        redisCacheConfigurationMap.put(USER_CACHE, createConfig(5, ChronoUnit.MINUTES));

        return RedisCacheManager
            .builder(redisConnectionFactory)
            .withInitialCacheConfigurations(redisCacheConfigurationMap)
            .build();
    }

    private static RedisCacheConfiguration createConfig(long time, ChronoUnit temporalUnit) {
        return RedisCacheConfiguration.defaultCacheConfig()
            .entryTtl(Duration.of(time, temporalUnit));
    }

	/*
	@Bean
	public RedisCacheConfiguration cacheConfiguration() {
		return RedisCacheConfiguration.defaultCacheConfig()
				.entryTtl(Duration.ofMinutes(60))
				.disableCachingNullValues()
				.serializeValuesWith(SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()));
	}
	
	@Bean
	public RedisCacheManagerBuilderCustomizer redisCacheManagerBuilderCustomizer() {
		return (buider) -> buider
				.withCacheConfiguration("walletCache", 
						RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofMinutes(10)));
	}*/
	
}
