package jin.redisluascript.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.redis.core.script.RedisScript;

@Configuration
public class ScriptConfig {

    @Bean
    public RedisScript<Boolean> script() { // Redis에 의해 실행될 Lua 스크립트를 나타내며, 스크립트의 실행 결과가 Boolean 타입으로 반환
        Resource scriptSource = new ClassPathResource("scripts/moneyTransfer.lua"); // Lua 스크립트 소스 설정
        /**
         * scriptSource: 앞서 정의한 Lua 스크립트의 위치를 가리키는 Resource 객체입니다.
         * Boolean.class: Lua 스크립트의 실행 결과가 Boolean 타입임을 나타냅니다.
         */
        return RedisScript.of(scriptSource, Boolean.class);
    }

}
