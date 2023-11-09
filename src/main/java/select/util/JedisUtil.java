package select.util;

import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.params.ScanParams;
import redis.clients.jedis.resps.ScanResult;
// import redis.clients.jedis.ScanParams;
// import redis.clients.jedis.ScanResult;
import select.base.Constants;
import select.system.dto.User;

import java.util.List;

/**
 * Project: OP-Bank
 * Module ID:
 * Comments:
 * JDK version used: JDK 17
 * Namespace: std
 * Author： YeTian
 * Create Date： 9/10/2023
 * Modified By： None
 * Modified Date: None
 * Why & What is modified: None
 * Version: 0.1.0
 */
@Component
public class JedisUtil {
    public void tokenToJedis(User user){
        Jedis jedis = getSource() ;
        jedis.set(user.getEmail() , user.getToken()) ;
        // Assuming user.getEmail() returns the key you want to expire
String key1 = user.getEmail();

// Set the expiration time in seconds (Constants.TOKEN_EXPIRED_TIME is in seconds)
int expirationTimeSeconds = Constants.TOKEN_EXPIRED_TIME;
jedis.pexpire(key1, expirationTimeSeconds);

        jedis.set(user.getToken() , user.getEmail()) ;
// Assuming user.getToken() returns the key you want to expire
String key2 = user.getToken();

// Set the expiration time in milliseconds (Constants.TOKEN_EXPIRED_TIME is in milliseconds)
long expirationTimeMillis = Constants.TOKEN_EXPIRED_TIME;
jedis.pexpire(key2, expirationTimeMillis);        Long currentTime = System.currentTimeMillis() ;
        jedis.set(user.getEmail()+user.getToken() ,currentTime.toString()) ;

        jedis.close();
        System.out.println("---"+ jedis.get(user.getEmail()));
        System.out.println("--"+ jedis.get(user.getToken()));

    }

    public void delString(String key){
        try {
            Jedis jedis = getSource() ;
            ScanParams scanParams = new ScanParams() ;
            StringBuilder paramKey = new StringBuilder("*").append(key).append("*") ;
            scanParams.match(paramKey.toString()) ;
            scanParams.count(1000) ;
            ScanResult<String> sr = jedis.scan("0" , scanParams) ;
            List<String> list = sr.getResult() ;
            for(String delKey : list){
                jedis.del(delKey) ;
            }
        }catch (Exception e){
            e.printStackTrace();
        }


    }

    public Jedis getSource(){
        return new Jedis("localhost" , 6379) ;
    }

}
