package select.system.service;

import select.base.Result;
import select.system.dto.User;
import select.util.PageBean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * Project: OP-Bank
 * Module ID:
 * Comments: Functional interface
 * JDK version used: JDK 17
 * Namespace: std
 * Author： YeTian
 * Create Date： 7/10/2023
 * Modified By： None
 * Modified Date: None
 * Why & What is modified: None
 * Version: 0.1.0
 */
public interface UserService {


    public User selectById(int userId) ;

    public List<User> selectAll() ;

    public boolean insertOne(User user) ;

    public boolean insertMany(List<User> userList) ;

    public boolean updateOne(User user) ;

    public boolean deleteById(int userId) ;

    public List<User> SelectByStartIndexAndPageSize(int startIndex , int pageSize) ;

    public List<User> selectByMap(Map<String, Object> map) ;

    public List<User> SelectByPageBean(PageBean pageBean) ;

    public List<User> selectByLike(Map<String , Object> map) ;

    public Result loginCheck (String email, String password, HttpServletResponse response) ;

    // public Result login (String email, String password) ;


    //query

    //transfer
    public String  transferAccount(double accountMoney , int targetAccount , HttpServletRequest request) ;

    //save
    public String saveMoney(double accountMoney , HttpServletRequest request) ;

    //withdraw
    public String withdrawMoney(double accountMoney , HttpServletRequest request) ;

    Result signup(User user);

}
