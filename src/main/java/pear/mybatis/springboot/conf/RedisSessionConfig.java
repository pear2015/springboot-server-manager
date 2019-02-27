package pear.mybatis.springboot.conf;

import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * Created by PEAR on 2019/2/27.
 */
@Configuration
@EnableRedisHttpSession
public class RedisSessionConfig {
}
