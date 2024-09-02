package jin.redisluascript;

import jin.redisluascript.service.MoneyTransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootApplication
public class RedisLuaScriptApplication implements CommandLineRunner { // CommandLineRunner: 이 인터페이스는 run 메서드를 제공하여 애플리케이션 시작 시 실행할 코드를 정의

    // SpringApplication.run(...): 스프링 애플리케이션을 시작하고, RedisLuaScriptApplication 클래스에서 정의된 설정을 기반으로 컨텍스트를 초기화
    public static void main(String[] args) {
        SpringApplication.run(RedisLuaScriptApplication.class, args);
    }

    @Autowired
    private RedisTemplate<String, String> redisTemplate; // Redis와 상호 작용하는 데 사용되는 템플릿 클래스

    @Autowired
    private MoneyTransferService service; // 설명한 계좌 간 금액 전송 로직을 처리하는 서비스 클래스

    @Override
    public void run(String... args) throws Exception { // run: CommandLineRunner 인터페이스의 유일한 메서드로, 애플리케이션이 시작될 때 실행

        // 계좌 초기화
        this.redisTemplate.opsForHash().put("account", "a", "100"); // Redis에 account라는 해시(hash) 키를 만들고, 계좌 a와 b의 초기 잔액을 각각 100과 20으로 설정
        this.redisTemplate.opsForHash().put("account", "b",  "20");

        // 금액 전송
        this.service.transfer("a", "b", 30); // transfer 메서드를 호출하여 계좌 a에서 계좌 b로 30의 금액을 전송

        // 결과 확인
        System.out.println( // 계좌 a와 b의 최종 잔액을 콘솔에 출력
                this.redisTemplate.opsForHash().get("account", "a") // redisTemplate.opsForHash().get(...): Redis에서 account 해시 키에 저장된 a와 b의 잔액을 가져옵니다.
        );
        System.out.println(
                this.redisTemplate.opsForHash().get("account", "b")
        );
    }
}
