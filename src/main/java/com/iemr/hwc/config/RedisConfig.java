package com.iemr.hwc.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.session.data.redis.config.ConfigureRedisAction;

import com.iemr.hwc.data.login.Users;

@Configuration
@EnableCaching
public class RedisConfig {

	@Bean
	public ConfigureRedisAction configureRedisAction() {
		return ConfigureRedisAction.NO_OP;
	}

	@Bean
	public RedisTemplate<String, Users> redisTemplate(RedisConnectionFactory factory) {
		RedisTemplate<String, Users> template = new RedisTemplate<>();
		template.setConnectionFactory(factory);

		// Use StringRedisSerializer for keys (userId)
		template.setKeySerializer(new StringRedisSerializer());

		// Use Jackson2JsonRedisSerializer for values (Users objects)
		Jackson2JsonRedisSerializer<Users> serializer = new Jackson2JsonRedisSerializer<>(Users.class);
		template.setValueSerializer(serializer);

		return template;
	}

}
