package kunuz.uz.api.service;

//import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class RedisService {

    private final StringRedisTemplate redisTemplate;

    public RedisService(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void save(String key, String value, Duration ttl) {
        redisTemplate.opsForValue().set(key, value, ttl);
    }

    public boolean saveIfAbsent(String key, String value, Duration ttl) {
        Boolean success = redisTemplate.opsForValue().setIfAbsent(key, value, ttl);
        return Boolean.TRUE.equals(success);
    }

    public boolean saveIfAbsent(String key, String value) {
        Boolean success = redisTemplate.opsForValue().setIfAbsent(key, value);
        return Boolean.TRUE.equals(success);
    }

    public String get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public void delete(String key) {
        redisTemplate.delete(key);
    }

    public void increment(String key, Duration window) {
        Long count = redisTemplate.opsForValue().increment(key);
        if(count != null && count == 1){
            redisTemplate.expire(key, window);
        }
    }


}
