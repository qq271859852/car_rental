package com.example.demo.config;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * local cache(Caffeine) configuration
 *
 * @author zhouxiong
 */
@Configuration
public class CacheConfig extends CachingConfigurerSupport {

    private static final String TTL_DELIMITER = "#";

    @Bean
    @Override
    public CacheManager cacheManager() {
        return new CustomCaffeineCacheManager();
    }

    private class CustomCaffeineCacheManager extends CaffeineCacheManager {
        private Map<String, Caffeine> cacheBuilderMap = new ConcurrentHashMap<>();

        private Caffeine<Object, Object> defaultCacheBuilder = Caffeine.newBuilder()
            .expireAfterWrite(60, TimeUnit.SECONDS);

        @Override
        protected Cache<Object, Object> createNativeCaffeineCache(String name) {
            String[] array = StringUtils.delimitedListToStringArray(name, TTL_DELIMITER);
            if (ArrayUtils.isNotEmpty(array) && array.length > 1) {
                Caffeine cacheBuilder = cacheBuilderMap.computeIfAbsent(array[1], k -> {
                    int ttl = Integer.parseInt(array[1]);
                    return Caffeine.newBuilder().expireAfterWrite(ttl, TimeUnit.SECONDS);
                });
                return cacheBuilder.build();
            }
            // 未指定过期时间，使用default进行构建
            return defaultCacheBuilder.build();
        }
    }
}
