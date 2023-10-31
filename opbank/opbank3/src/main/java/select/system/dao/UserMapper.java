package select.system.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import select.system.dto.User;
import select.util.PageBean;

import java.util.List;
import java.util.Map;

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
@Repository
@Mapper
public interface UserMapper {

    public User selectByEmail(String email)  ;

    public User selectById (int userId ) ;

    public User selectByPayID (int payId ) ;

    public User selectByAccountNumber (int accountNumber);

    public List<User> selectAll() ;

    public boolean insertOne(User user) ;

    public boolean insertMany(List<User> userList) ;

    public boolean updateOne(User user) ;

    public boolean deleteById(int userId) ;

    public List<User> SelectByStartIndexAndPageSize(int startIndex , int pageSize) ;

    public List<User> selectByMap(Map<String, Object> map) ;

    public List<User> SelectByPageBean(PageBean pageBean) ;

    public List<User> selectByLike(Map<String , Object> map) ;

    // public User selectByEmailUser(String email) ;

    //Transfer out for renewal
    public boolean updateAccountOut (double accountMoney , String email) ;
    //Transfer in for renewal
    public boolean updateAccountIn (double accountMoney , String email) ;

    //Transfer out log insert
    public boolean accountOutInsert(String email ,int accountNumber , double accountMoney  , String accountType) ;
    public boolean accountOutInsert(String email ,int accountNumber , double accountMoney  , int targetAccount,
     String accountType) ;

    //Transfer in log insert
    public boolean accountInInsert(String email ,int accountNumber , double accountMoney  , String accountType) ;
    public boolean accountInInsert(String email ,int accountNumber , double accountMoney  , 
    int targetAccount, String accountType) ;

}
