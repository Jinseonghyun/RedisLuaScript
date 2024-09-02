package jin.redisluascript.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MoneyTransferService {

    @Autowired
    private RedisScript<Boolean> script; // moneyTransfer.lua 스크립트를 담고 있는 RedisScript 객체를 참조합니다. 스크립트는 계좌 간 금액 전송 로직을 포함하고 있으며, 결과는 Boolean 타입으로 반환

    @Autowired
    private RedisTemplate<String, String> redisTemplate; //  Redis 서버와 상호 작용하는 템플릿 클래스입니다. 이 클래스는 Redis의 데이터를 조회하거나 수정하는 작업을 쉽게 수행할 수 있게 해줍니다.

    /**
     * 두 계좌 간의 금액을 전송하는 메서드입니다.
     * fromAccount는 돈을 송금하는 계좌,
     * toAccount는 돈을 받는 계좌
     * amount는 전송할 금액을 의미
     */
    public void transfer(String fromAccount, String toAccount, int amount){
        this.redisTemplate
                .execute(script, List.of(fromAccount, toAccount), String.valueOf(amount));
    }

}
