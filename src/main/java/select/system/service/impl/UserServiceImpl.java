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
import select.system.dao.UserMessageMapper;
import select.system.dao.MyTransactionMapper;
import select.system.dto.User;
import select.system.dto.Card;
import select.system.dto.UserMessage;
import select.system.dto.UserProfile;
import select.system.dto.UpdatedProfile;
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

import java.net.http.HttpResponse;
import java.util.Date;
import java.util.List;
import java.util.Map;
import select.system.dto.UserProfile;

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

    @Autowired
    UserMessageMapper userMessageMapper;

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

            int number = generateUniqueRandomNumber();
            System.out.println("number: "+number);
            String expiration_date=  "2028-10-31";
            boolean is_blocked=false;
            boolean is_activated=false;
            cardMapper.insertOne(number, idOfUser, is_blocked, is_activated, expiration_date);
            
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
        System.out.println("dest acc Number is:"+destAccountObj.getAccountNumber());
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
                     System.out.println("mt has been made");
                     System.out.println("heyyyyyyyyyyyyy"+mt.getAmount());
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
    public boolean payByPayID(int payId, double amount, int userID, HttpServletResponse response){
        System.out.println("payId: "+payId);
        System.out.println("userID: "+userID);
        User destUserObj = userMapper.selectByPayID(payId);
        System.out.println("destUzserObk: "+destUserObj);
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
                    MyTransaction mt = new MyTransaction(senderAccountNumber, amount, receiverAccountNumber);
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
       
    }
    
    //query
    public User selectByEmail(String email) {
        return userMapper.selectByEmail(email) ;
    }

    public double getAccountMoneyFunction(int userID, HttpServletResponse response){
        Account account = accountMapper.selectByUserID(userID);
        double money = account.getAmount();
        response.setStatus(HttpServletResponse.SC_OK);
        return money;
    }
    public double getTotalDebit(int userID, HttpServletResponse response){
        System.out.println("getTotalDebit called");
        Account account = accountMapper.selectByUserID(userID);
        int accountNumber = account.getAccountNumber();
        System.out.println("getTotalDebit accNo: "+accountNumber);
        response.setStatus(HttpServletResponse.SC_OK);
        double amount = myTransactionMapper.selectTotalDebitAmount(accountNumber);
        return amount;
    }

    public double getTotalCredit(int userID, HttpServletResponse response){
            Account account = accountMapper.selectByUserID(userID);
            int accountNumber = account.getAccountNumber();
            double amount = myTransactionMapper.selectTotalCreditAmount(accountNumber);
             return amount;
    }

    public double getLastDebit(int userID, HttpServletResponse response){
            Account account = accountMapper.selectByUserID(userID);
            int accountNumber = account.getAccountNumber();
            System.out.println("getLastDebit accNo: "+accountNumber);
            double amount = myTransactionMapper.selectLastDebitAmount(accountNumber);
            return amount;
    }

    public double getLastCredit(int userID, HttpServletResponse response){
            Account account = accountMapper.selectByUserID(userID);
            int accountNumber = account.getAccountNumber();
            double amount = myTransactionMapper.selectLastCreditAmount(accountNumber);
             return amount;
    }

    @Override
    public List<MyTransaction> getAllTransactions(int userID, HttpServletResponse response) {
        Account account = accountMapper.selectByUserID(userID);
        System.out.println("account data:"+account.getAccountNumber());
        int accountNumber = account.getAccountNumber();
        if(accountNumber <= 0 ){
        response.setStatus(HttpServletResponse.SC_NOT_FOUND); 
        }
        response.setStatus(HttpServletResponse.SC_OK);
        return myTransactionMapper.selectByAccountNumber(accountNumber);
    }

    @Override
public UserProfile getProfile(int userId, HttpServletResponse response) {
    // Fetch the user profile from the database by userID
    User user = userMapper.selectById(userId);
    // Check if the user was found
    if (user == null) {
        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        return null;
    }
    String fn = user.getFirstName();
    String number = user.getMobileNumber();
    String email = user.getEmail();
    Date bday = user.getDateOfBirth();
    String pass = user.getuserPassword();
    int payID = user.getPayId();
    int userID = userId;
    Account acc = accountMapper.selectByUserID(userID);
    int accountNumber = acc.getAccountNumber();


    UserProfile userProfile = new UserProfile(fn, number, email, bday, accountNumber, pass, payID);
    response.setStatus(HttpServletResponse.SC_OK);
    return userProfile;
}


public boolean getProfile(String firstName, String mobileNumber, String email, Date dateOfBirth,
                           String password, int userID, HttpServletResponse response) {
    try {
        // Fetch the user profile from the database based on the parameters
       // UpdatedProfile updatedProfile = new UpdatedProfile(firstName, mobileNumber, email, dateOfBirth, password);
            System.out.println("email:"+email);
            userMapper.updateProfile(userID, firstName, mobileNumber, email, dateOfBirth, password);
            // If a user profile is found, you can set it in the response or perform other actions
            response.setStatus(HttpServletResponse.SC_OK);
            // Set the user profile data in the response or perform other actions
            return true; // Indicate success
    } catch (Exception e) {
        // Handle exceptions and set an appropriate response status
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        return false; // Indicate failure
    }
}



@Override
    public boolean verifyCard(int id, int number, String expirationDate, int userId, HttpServletResponse response) {
        try {
            // You can perform additional validations and logic here if needed.
            // For example, check if the card details are valid before inserting into the database.

            // Assuming cardMapper.insertOne inserts the card details into the database.
            // This method will vary based on your database and SQL mapper.
            Card c = cardMapper.selectById(id);
            System.out.println("userId is:"+userId);
            if(c==null){
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                return false;
            }
            System.out.println("c.getExpirstion"+c.getExpirationDate());
                        System.out.println("Expiration: "+expirationDate);
                        System.out.println("getNumber"+c.getNumber());
                        System.out.println("number: "+number);


            if(c.getExpirationDate().equals(expirationDate)){
                if(c.getNumber()==number){
                System.out.println("SC IS OK");

                cardMapper.activateCard(userId);
                response.setStatus(HttpServletResponse.SC_OK);
                return true;
                } else{
                     response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                return false;
                }
                
            } else{
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                return false;
            }
        } catch (Exception e) {
            // Handle any exceptions that may occur during card verification.
            e.printStackTrace();
            // You can set an appropriate response in your HttpServletResponse.
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return false;
        }
    }
    @Override
        public boolean getActiveStatus(int userID, HttpServletResponse response){
            boolean is_activated = cardMapper.getActiveStatus(userID);
            System.out.println("is_activated is: "+is_activated);
           // is_activated = true;
            return is_activated;
        }


         @Override
        public boolean getBlockedStatus(int userID, HttpServletResponse response){
            boolean is_blocked = cardMapper.getBlockedStatus(userID);
            System.out.println("is_activated is: "+is_blocked);
           // is_activated = true;
            return is_blocked;
        }
       
     @Override
     public boolean blockCard(int userID, HttpServletResponse response){
        boolean status = cardMapper.blockCard(userID);
        if(status==true){
        response.setStatus(HttpServletResponse.SC_OK);
        return status;
        } else{
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return status;
        }
     }

      @Override
     public boolean unblockCard(int userID, HttpServletResponse response){
        boolean status = cardMapper.unblockCard(userID);
        if(status==true){
        response.setStatus(HttpServletResponse.SC_OK);
        return status;
        } else{
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return status;
        }
     }

     @Override
     public boolean insertChat(String userMessage, String answer, int userID, HttpServletResponse response)
     {  
        System.out.println("answer is: "+answer);
        boolean result = userMessageMapper.insertMessage(userMessage, answer, userID);
        return result;
     }
     
     @Override
     public List<UserMessage> getAllMessages(int userID, HttpServletResponse response)
     {
       return userMessageMapper.getAllMessages(userID);
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
        Random random = new Random();
        // Generate a random number between 100000 and 999999
        int randomValue = 100000 + random.nextInt(900000);
        System.out.println("random value: " + randomValue);
    
        return randomValue;
    }
    
    
}
