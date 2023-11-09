package select.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.thymeleaf.util.StringUtils;
import redis.clients.jedis.Jedis;
import select.base.Constants;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
public class AuthenticationInterceptor implements HandlerInterceptor {
    @Autowired
    TokenUtil tokenUtil ;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {

        String token = request.getHeader("token") ;
        Jedis jedis = new Jedis("localhost" , 6379) ;
        if(StringUtils.isEmpty(token)){
            response.sendRedirect("/sys/user/login");
            return false ;
        }
        System.out.println("The user information value in redis is：" + jedis.get(token));

        String userName = jedis.get(token) ;
        System.out.println("token indicates the user name："+ userName );
        if(userName!=null && !userName.trim().equals("")){
            System.out.println("token match success");
            Long tokenBirthTime = Long.valueOf(jedis.get(userName+token)) ;
            Long diff = System.currentTimeMillis()-tokenBirthTime ;
            if(diff >  Constants.TOKEN_RESET_TIME){
                jedis.expire(userName , Constants.TOKEN_EXPIRED_TIME) ;
                jedis.expire(token , Constants.TOKEN_EXPIRED_TIME) ;
                System.out.println("validity update！");
                Long newBirthTime = System.currentTimeMillis() ;
                jedis.set(userName+token , newBirthTime.toString()) ;
            }
            return true ;
        }

        response.sendRedirect("/sys/user/login");
        return false ;
    }



}
