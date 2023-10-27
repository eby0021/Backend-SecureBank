package select.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletRequestAttributes;
import select.system.dto.User;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * Project: OP-Bank
 * Module ID:
 * Comments:
 * JDK version used: JDK 17
 * Namespace: std
 * Author： YeTian
 * Create Date： 9/10/2023
 * Modified By： YeTian
 * Modified Date: 11/10/2023
 * Why & What is modified: None
 * Version: 0.1.0
 */
@Component
public class TokenUtil {
    /***
    * 11/10/2023
    *
    * * @param user
    * * @return : java.lang.String
    */
    public String generateToken(User user){
        Date start = new Date() ;
        long currentTime = System.currentTimeMillis() + 60*60*1000 ;
        Date end = new Date(currentTime);
        String token = "" ;
        token = JWT.create()
                .withAudience(Integer.toString(user.getUserId()))
                .withAudience(user.getEmail())
                .withIssuedAt(start)
                .withIssuedAt(end)
                .sign(Algorithm.HMAC256(user.getuserPassword())) ;
        return token ;
    }

    /***
    * 11/10/2023
    *
    * * @param token
     * @param key
    * * @return : java.lang.String
    */
    public static String get(String token , String key){
        List<String> list = JWT.decode(token).getAudience() ;
        String userId = list.get(0) ;
        return userId ;
    }

    /*
     * get token
     * */
    /*public String getToken(HttpServletRequest request)  {
        Cookie[] cookies = request.getCookies() ;
        for(Cookie c : cookies){
            if(c.getName() .equals( "token")){
                return c.getValue() ;
            }
        }
        return null ;
    }*/



    
    
}
