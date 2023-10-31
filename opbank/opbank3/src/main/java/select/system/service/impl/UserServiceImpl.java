package select.system.service.impl;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;
import select.base.Constants;
import select.base.Result;
import select.constants.BaseEnums;
import select.constants.TransactionType;
import select.system.dao.UserMapper;
import select.system.dao.AccountMapper;
import select.system.dao.CardMapper;
import select.system.dao.BillMapper;
import select.system.dao.MyTransactionMapper;
import select.system.dto.User;
import select.system.dto.Account;
import select.system.dto.Bill;
import select.system.dto.MyTransaction;
import select.system.service.UserService;
import select.util.JedisUtil;
import select.util.PageBean;
import select.util.Results;
import select.util.TokenUtil;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * Project: OP-Bank
 * Module ID:
 * Comments: redis implements the second-level cache of tokens
 * JDK version used: JDK 17
 * Namespace:
 * Author： YeTian
 * Create Date： 9/10/2023
 * Modified By： None
 * Modified Date: None
 * Why & What is modified: None
 * Version: 0.1.0
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper ;

    @Autowired
    AccountMapper accountMapper ;

    @Autowired
    CardMapper cardMapper ;

    @Autowired
    MyTransactionMapper myTransactionMapper ;

    @Autowired
    TokenUtil tokenUtil ;

    @Autowired
    JedisUtil jedisUtil ;

    @Autowired
    BillMapper billMapper ;

    // public User selectByEmail(String email) {
    //   return  userMapper.selectByEmail(email) ;
    // }

    public User selectById(int userId){
        return userMapper.selectById(userId) ;
    }

    public List<User> selectAll(){
        return userMapper.selectAll() ;
    }

    public void insertOne(User user, HttpServletResponse response) {
        try {
            // Attempt to insert the user
            userMapper.insertOne(user);
            
            // After inserting the user, you can retrieve the user ID and perform additional actions.
            User resultObj = userMapper.selectByEmail(user.getEmail());
            int idOfUser = resultObj.getUserId();
            System.out.println("UserID is: " + idOfUser);
    
            // Insert other related data (e.g., account) using the retrieved user ID
            accountMapper.insertOne(idOfUser);
            
            // If everything was successful, set the response status to OK (200).
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (Exception e) {
            // Handle exceptions here
            e.printStackTrace();
            System.out.println("An error occurred: " + e.getMessage());
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
    

    public boolean insertMany(List<User> userList) {
        return userMapper.insertMany(userList) ;
    }
    public boolean updateOne(User user){
        return userMapper.updateOne(user) ;
    }

    public boolean deleteById(int userId){
        return userMapper.deleteById(userId) ;
    }

    public List<User> SelectByStartIndexAndPageSize(int startIndex , int pageSize) {
        return userMapper.SelectByStartIndexAndPageSize(startIndex,pageSize) ;
    }

    public List<User> selectByMap(Map<String ,Object> map){
        return userMapper.selectByMap(map) ;
    }

    public List<User> SelectByPageBean(PageBean pageBean) {
        return userMapper.SelectByPageBean(pageBean) ;
    }

    public List<User> selectByLike(Map<String , Object> map){
        return userMapper.selectByLike(map) ;
    }

    public int loginCheck(String email, String password, HttpServletResponse response){
       // System.out.println("I am in loginCheck of UserServiceImpl");
        User user1 = userMapper.selectByEmail(email) ;
       // System.out.println("user1 is:"+user1.getEmail());
        if(user1 == null ){
            System.out.println("user not found");
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return 0 ;
        } 
        // else{
        //    System.out.println("user password is:"+user1.getuserPassword());
        //    System.out.println("password is:"+password);
        //    System.out.println("equals is: "+user1.getuserPassword().equals(password));
        // }
        //             return Results.successWithData(user1);
    
        else if(!user1.getuserPassword().equals(password)){
            System.out.println("incorrect password");
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return 0 ;
        }
        else{
            System.out.println("login successful");
            response.setStatus(HttpServletResponse.SC_OK);
            return user1.getUserId();
        }
    }
        // Jedis jedis = jedisUtil.getSource() ;
        // String jedisKey = jedis.get(email) ;
        // if(jedisKey != null){
        //     jedisUtil.delString(email);
        // }
        // String token = tokenUtil.generateToken(user1) ;
        // System.out.println("token:" + token);
        // user1.setToken(token);
        // // Set the token in the response header
        // response.setHeader("Authorization", token);
        // jedisUtil.tokenToJedis(user1);
       
    


    public boolean payByAccountNumber(int destAcc, int bsbNumber, double amount, String reason,
     int userID, HttpServletResponse response){
        System.out.println("===========================destAcc is"+destAcc);
        System.out.println("===========================bsbNumber is"+bsbNumber);
                System.out.println("===========================userID is"+userID);

        Account destAccountObj = accountMapper.selectByAccountNumber(destAcc);
        //System.out.println("dest acc Number is:"+destAccountObj.getAccountNumber());
      //  double currAmount = destAccountObj.getAmount();
        if(bsbNumber == destAccountObj.getBsbNumber()){
            System.out.println("bsb number entered is correct");
            Account senderAccount = accountMapper.selectByUserID(userID);
            if(senderAccount != null){
                System.out.println("sender acc is not null. Account:"+senderAccount);
                double senderAmount = senderAccount.getAmount();
                if(senderAmount >= amount){
                    // senderAccount.setAmount(senderAmount - amount);
                    // destAccountObj.setAmount(currAmount + amount);
                    // System.out.println("sender amount:"+senderAccount.getAmount());
                    // System.out.println("receiver amount:"+destAccountObj.getAmount());
                    accountMapper.saveMoney(destAccountObj.getAccountNumber(), amount);
                    accountMapper.withdrawMoney(senderAccount.getAccountNumber(), amount);
                    int senderAccountNumber = senderAccount.getAccountNumber();
                    int receiverAccountNumber = destAccountObj.getAccountNumber();
                   // System.out.println("reason is: "+reason);
                    MyTransaction mt = new MyTransaction(senderAccountNumber, receiverAccountNumber,
                     reason, amount);
                    boolean trans = myTransactionMapper.insertOne(mt);
                    if(trans) System.out.println("transaction saved successfully");
                    response.setStatus(HttpServletResponse.SC_OK);
                    return true;
                } else{
                    System.out.println("Insufficient funds");
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    return false;
                }
            } else{
                System.out.println("sender's userID does not match with any account");
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                return false;
            }
         
        } else{
            System.out.println("Incorrect bsb number");
            return false;
        }
    }
    public boolean payByPayID(int payID, double amount, String reason, int userID,
     HttpServletResponse response){
        User destUserObj = userMapper.selectByPayID(payID);
        int destUserID = destUserObj.getUserId();
        Account destAccountObj = accountMapper.selectByUserID(destUserID);
        System.out.println("dest acc Number is:"+destAccountObj.getAccountNumber());
        //double currAmount = destAccountObj.getAmount();
            Account senderAccount = accountMapper.selectByUserID(userID);
            if(senderAccount != null){
                System.out.println("sender acc is not null. Account:"+senderAccount);
                double senderAmount = senderAccount.getAmount();
                if(senderAmount >= amount){
                    // senderAccount.setAmount(senderAmount - amount);
                    // destAccountObj.setAmount(currAmount + amount);
                    // System.out.println("sender amount:"+senderAccount.getAmount());
                    // System.out.println("receiver amount:"+destAccountObj.getAmount());
                    accountMapper.saveMoney(destAccountObj.getAccountNumber(), amount);
                    accountMapper.withdrawMoney(senderAccount.getAccountNumber(), amount);
                    int senderAccountNumber = senderAccount.getAccountNumber();
                    int receiverAccountNumber = destAccountObj.getAccountNumber();
                   //System.out.println("reason is: "+reason);
                    MyTransaction mt = new MyTransaction(senderAccountNumber, receiverAccountNumber,
                     reason, amount);
                    boolean trans = myTransactionMapper.insertOne(mt);
                    if(trans) System.out.println("transaction saved successfully");
                    response.setStatus(HttpServletResponse.SC_OK);
                    return true;
                } else{
                    System.out.println("Insufficient funds");
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    return false;
                }
            } else{
                System.out.println("sender's userID does not match with any account");
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                return false;
            }
    }


    public boolean payBill(int referenceNumber, int billerCode, double amount, String nickname, int userID,
    HttpServletResponse response) {
        Account billerAccount = accountMapper.selectByUserID(billerCode);
    
        if (billerAccount != null) {
            System.out.println("Biller account found. Account: " + billerAccount.getAccountNumber());
            //double billerAmount = billerAccount.getAmount();
            
            Account senderAccount = accountMapper.selectByUserID(userID);
            
            if (senderAccount != null) {
                System.out.println("Sender account is not null. Account: " + senderAccount.getAccountNumber());
                double senderAmount = senderAccount.getAmount();
    
                if (senderAmount >= amount) {
                    System.out.println("Sufficient funds in sender's account");
                    
                    // Perform the money transfer
                    accountMapper.withdrawMoney(senderAccount.getAccountNumber(), amount);
                    boolean isPaid = true;
                    int sender_accountNumber = senderAccount.getAccountNumber();
                    MyTransaction mt = new MyTransaction(sender_accountNumber, referenceNumber, amount);
                    boolean trans = myTransactionMapper.insertOneBill(mt);
                    if(trans) System.out.println("transaction saved successfully");
                    // List<MyTransaction> transactions = myTransactionMapper.selectByAccountNumber(sender_accountNumber);
                    // for(int i=0; i<transactions.size(); i++){
                    //     System.out.println(transactions.get(i));
                    // }
                    response.setStatus(HttpServletResponse.SC_OK);
                    return  billMapper.insertOne(billerCode, referenceNumber, amount, nickname, isPaid);
                   
                } else {
                    System.out.println("Insufficient funds in sender's account");
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    return false;
                }
            } else {
                System.out.println("Sender's userID does not match with any account");
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                return false;
            }
        } else {
            System.out.println("Biller account not found");
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return false;
        }
    }
    
    //query
    public User selectByEmail(String email) {
        return userMapper.selectByEmail(email) ;
    }

    //transfer
    // public String transferAccount(double accountMoney , int targetAccount , HttpServletRequest request){
    //     Jedis jedis = jedisUtil.getSource() ;
    //     String token = request.getHeader("token") ;
    //     String email = jedis.get(token) ;
    //     User user = userMapper.selectByEmail(email) ;
    //     double nowAccountMoney = user.getAccountMoney() ;
    //     if(accountMoney > nowAccountMoney){
    //         return "余额不足" ;
    //     }
    //     User user1 = userMapper.selectByAccountNumber(targetAccount) ;
    //     if (user1.equals(null)){
    //         return "对方账户不存在" ;
    //     }
    //     //转出账户余额更新
    //     boolean result = userMapper.updateAccountOut(accountMoney , email) ;
    //     //转入账户余额更新
    //     boolean result1 = userMapper.updateAccountIn(accountMoney , user1.getEmail()) ;
    //     if ((result == false)||(result1 == false) ){
    //         return "转账操作失败" ;
    //     }
    //     //转账记录生成------------
    //     //String accountType = TransactionType.WITHDRAWMONEY ;
    //     //出账记录生成
    //     boolean insertReult = userMapper.accountOutInsert(email ,user.getAccountNumber() ,  accountMoney , targetAccount , TransactionType.WITHDRAWMONEY ) ;
    //     //入账记录生成
    //     //String accountType1 = TransactionType.SAVEMONEY ;
    //     boolean insertReult1 = userMapper.accountInInsert(email ,user.getAccountNumber() ,  accountMoney , targetAccount , TransactionType.SAVEMONEY  ) ;

    //     if((insertReult == false) || (insertReult1 == false)){
    //         return "转账记录生成失败" ;
    //     }

    //     return "转账成功！" ;
    // }

    //存钱
    // public String saveMoney(double accountMoney , HttpServletRequest request){
    //     Jedis jedis = jedisUtil.getSource() ;
    //     String token = request.getHeader("token") ;
    //     String email = jedis.get(token) ;
    //     User user = userMapper.selectByEmail(email) ;
    //     //存入余额更新
    //     boolean result = userMapper.updateAccountIn(accountMoney , email) ;
    //     if(result = false){
    //         return "存入失败" ;
    //     }
    //     //存入记录生成
    //     boolean insertResult = userMapper.accountInInsert(email , user.getAccountNumber() , accountMoney, TransactionType.SAVEMONEY) ;
    //     if((insertResult == false)){
    //         return "入账记录生成失败" ;
    //     }
    //     return "Save" + accountMoney + " AUD successfully"  ;
    // }

    //取钱
    // public String withdrawMoney(double accountMoney , HttpServletRequest request){
    //     Jedis jedis = jedisUtil.getSource() ;
    //     String token = request.getHeader("token") ;
    //     String email = jedis.get(token) ;
    //     User user = userMapper.selectByEmail(email) ;
    //     double nowAccountMoney = user.getAccountNumber() ;
    //     if(accountMoney > nowAccountMoney){
    //         return "Insufficient balance" ;
    //     }
    //     boolean result = userMapper.updateAccountOut(accountMoney , email) ;
    //     if(result = false){
    //         return "Withdrawal failure" ;
    //     }
    //     //出账记录生成
    //     boolean insertResult = userMapper.accountOutInsert(email ,user.getAccountNumber() , accountMoney , TransactionType.WITHDRAWMONEY) ;
    //     if((insertResult == false)){
    //         return "出账记录生成失败" ;
    //     }
    //     return "Withdraw" + accountMoney + " AUD successfully"  ;
    // }

    public Result signup(User user) {
        try {
            // Perform data validation here, e.g., check if the email is unique, perform password hashing, etc.
            // Implement validation based on your specific requirements and security practices.

            // You can add your own validation logic here.
            // For example, if you want to check if the email is unique:
            User existingUser = userMapper.selectByEmail(user.getEmail());
            if (existingUser != null) {
                return Results.failure("User with this email already exists.");
            }

            // Assuming validation is successful, you can insert the user into the database using the userMapper.
            boolean success = userMapper.insertOne(user);

            if (success) {
                // Registration successful
                return Results.success("User registered successfully");
            } else {
                // Registration failed
                return Results.failure("User registration failed");
            }
        } catch (Exception e) {
            // Handle any exceptions that may occur during the registration process, e.g., database errors.
            return Results.failure("An error occurred during user registration");
        }
    }

    public int generateUniqueRandomNumber() {
       // int currentTime = System.currentTimeMillis();
        Random random = new Random();
        // Generate a random number of up to 4 digits
        int randomValue = random.nextInt(10000);
    
        // Combine the current time and random number to create a 16-digit unique number
       // int uniqueNumber = (currentTime * 1000000L) + randomValue;
    
        return randomValue;
    }
    
}
