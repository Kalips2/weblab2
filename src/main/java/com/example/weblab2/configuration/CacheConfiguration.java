package com.example.weblab2.configuration;

import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Slf4j
@EnableCaching
@Configuration
@EnableScheduling
public class CacheConfiguration {

  // after 5 minutes cache should be evicted.
  public static final int CACHE_TIMEOUT = 1;

  // naming for cache managers that will be created
  public static final String ALBUMS = "albums";
  public static final String LABELS = "labels";
  public static final String ARTISTS = "actors";
  public static final String SONGS = "songs";


  @Scheduled(fixedRate = CACHE_TIMEOUT, timeUnit = TimeUnit.MINUTES)
  @CacheEvict(value = {
      ALBUMS, LABELS, ARTISTS, SONGS
  }, allEntries = true)
  public void evictCache() {
    log.info("All caches were evicted. Timeout is " + CACHE_TIMEOUT + " minutes!");
  }

  @Bean
  @Primary
  public CacheManager cacheManager() {
    return new ConcurrentMapCacheManager();
  }
}
