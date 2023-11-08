package select.system.service;

import select.base.Result;
import select.system.dto.MyTransaction;
import select.system.dto.User;
import select.util.PageBean;
import select.system.dto.UserProfile;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import select.system.dto.UserMessage;

import java.util.List;
import java.util.Map;
import java.net.http.HttpResponse;
import java.util.Date;


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

    public void insertOne(User user, HttpServletResponse response) ;

    public boolean insertMany(List<User> userList) ;

    public boolean updateOne(User user) ;

    public boolean deleteById(int userId) ;

    public List<User> SelectByStartIndexAndPageSize(int startIndex , int pageSize) ;

    public List<User> selectByMap(Map<String, Object> map) ;

    public List<User> SelectByPageBean(PageBean pageBean) ;

    public List<User> selectByLike(Map<String , Object> map) ;

    public int loginCheck (String email, String password, HttpServletResponse response) ;

    public boolean payByAccountNumber(int destAccNumber, int bsbNumber, double amount, String reason,
     int userID, HttpServletResponse response) ;

    public boolean payByPayID(int payID, double amount, int userID, HttpServletResponse response) ;

    public boolean payBill(int referenceNumber, int billerCode, double amount, String nickname, 
    int userID, HttpServletResponse response) ;

    List<MyTransaction> getAllTransactions(int userID, HttpServletResponse response);


    public double getAccountMoneyFunction(int userID, HttpServletResponse response);

    public double getTotalDebit(int userID, HttpServletResponse response);

    public double getTotalCredit(int userID, HttpServletResponse response);

    public double getLastDebit(int userID, HttpServletResponse response);

    public double getLastCredit(int userID, HttpServletResponse response);


    public UserProfile getProfile(int userID, HttpServletResponse response);


    public boolean getProfile(String firstName, String mobileNumber, String email, Date dateOfBirth,
                           String password, int userID, HttpServletResponse response);


    public boolean verifyCard(int id, int number, String expirationDate, int userId, HttpServletResponse response);


    public boolean getActiveStatus(int userID, HttpServletResponse response);


    public boolean getBlockedStatus(int userID, HttpServletResponse response);


    public boolean blockCard(int userID, HttpServletResponse response);


    public boolean unblockCard(int userID, HttpServletResponse response);

    public boolean insertChat(String userMessage, String answer, int userID, HttpServletResponse response);

    
    public List<UserMessage> getAllMessages(int userID, HttpServletResponse response);


    // //transfer
    // public String  transferAccount(double accountMoney , int targetAccount , HttpServletRequest request) ;

    // //save
    // public String saveMoney(double accountMoney , HttpServletRequest request) ;

    // //withdraw
    // public String withdrawMoney(double accountMoney , HttpServletRequest request) ;

    Result signup(User user);

}
