package select.system.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;
// import com.rivescript.RiveScript;
import select.base.Result;
import select.constants.BaseEnums;
import select.system.dto.User;
import select.system.dto.UserMessage;
import select.system.dto.UserProfile;
import select.system.dto.UpdatedProfile;
import select.system.dto.PayByAccountNumberReq;
import select.system.dto.PayByPayIDReq;
import select.system.dto.Message;
import select.system.dto.LoginRequest;
import select.system.dto.VerifyCard;
import select.system.dto.MyTransaction;
import select.system.dto.PayBillReq;
import select.system.service.UserService;
import select.util.PageBean;
import select.util.Results;
import com.rivescript.RiveScript;

import select.system.dto.UserMessageReq;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import select.system.chatbot.RiveScriptChatbot;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Project: OP-Bank
 * Module ID:
 * Comments:
 * JDK version used: JDK 17
 * Namespace: std
 * Author： YeTian
 * Create Date： 10/10/2023
 * Modified By： None
 * Modified Date: None
 * Why & What is modified: None
 * Version: 0.1.0
 */
@CrossOrigin(origins = "http://localhost:3000") // Replace with the URL of your frontend
@RestController
@RequestMapping("/sys/user")
public class UserController {

        // private RiveScript chatbot;


    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    UserService userService ;

    @Autowired
    private RiveScriptChatbot chatbot; // Inject the chatbot
    
   
    public String convertJsonToString(Object yourJsonObject) {
        try {
            // Convert the JSON object to a String
            return objectMapper.writeValueAsString(yourJsonObject);
        } catch (JsonProcessingException e) {
            // Handle the exception, e.g., log it or throw a custom exception
            e.printStackTrace();
            return "error";
        }
    }
    
        @GetMapping("/")
    public String home(){
        return "Hello your server is runing" ;
    }


    @GetMapping("/selectById")
    public Result selectByName(@RequestParam("userId") int userId ){
        return Results.successWithData(userService.selectById(userId) , BaseEnums.SUCCESS.code()) ;
    }

    @GetMapping("/selectAll")
    public Result selectAll(){
        return Results.successWithData(userService.selectAll() , BaseEnums.SUCCESS.code()) ;
    }
    
    @PostMapping("/insertMany")
    public Result insertMany(@RequestBody List<User> userList){
        return Results.successWithData(userService.insertMany(userList) , BaseEnums.SUCCESS.code()) ;
    }

    @PostMapping("/updateOne")
    public Result updateOne(@RequestBody User user){
        return Results.successWithData(userService.updateOne(user) , BaseEnums.SUCCESS.code()) ;
    }
    @PostMapping("/deleteById")
    public Result deleteById(@RequestParam int userId){
        return Results.successWithData(userService.deleteById(userId) , BaseEnums.SUCCESS.code()) ;
    }



    @GetMapping("/SelectByStartIndexAndPageSize")
    public Result SelectByStartIndexAndPageSize(
            @RequestParam("currentPage") int currentPage, @RequestParam("pageSize") int pageSize){
        List<User> userList = userService.SelectByStartIndexAndPageSize
                ((currentPage-1)*pageSize , pageSize) ;
        return Results.successWithData(userList , BaseEnums.SUCCESS.code()) ;

    }

    @GetMapping("SelectByMap")
    public Result SelectByMap(@RequestParam("currentPage")int currentPage , @RequestParam("pageSize") int pageSize){
        Map<String ,Object> map = new HashMap<>() ;
        map.put("startIndex" , (currentPage-1)*pageSize) ;
        map.put("pageSize" , pageSize) ;

        List<User> userList = userService.selectByMap(map) ;

        return Results.successWithData(userList , BaseEnums.SUCCESS.code()) ;
    }

    @GetMapping("SelectByPageBean")
    public Result SelectByPageBean(@RequestParam("currentPage") int currentPage , @RequestParam("pageSize") int pageSize){
        PageBean pageBean = new PageBean() ;
        pageBean.setStartIndex((currentPage-1)*pageSize);
        pageBean.setPageSize(pageSize);
        return Results.successWithData(userService.SelectByPageBean(pageBean) , BaseEnums.SUCCESS.code()) ;

    }

    @GetMapping("SelectByLike")
    public Result SelectByLike(@RequestParam("keywords") String keywords,
                               @RequestParam("currentPage") int currentPage,
                               @RequestParam("pageSize") int pageSize){
        Map<String,Object> map = new HashMap<>() ;
        map.put("keywords" , keywords) ;
        map.put("startIndex" , (currentPage-1)*pageSize) ;
        map.put("pageSize" , pageSize) ;
        userService.selectByLike(map) ;
        return Results.successWithData(userService.selectByLike(map) , BaseEnums.SUCCESS.code()) ;
    }


    @PostMapping("/loginCheck")
    public int loginCheck(@RequestBody LoginRequest loginRequest, HttpServletResponse response) {
        //System.out.println("I am in userController");
        System.out.println("getEmail:"+loginRequest.getEmail());
        System.out.println("getPassword:"+loginRequest.getPassword());
        return userService.loginCheck(loginRequest.getEmail(), loginRequest.getPassword(), response);
    }
    
    
@PostMapping("/insertOne")
    public void insertOne(@RequestBody User user, HttpServletResponse response){
        System.out.println("user email: "+user.getEmail());
        System.out.println("user fname: "+user.getFirstName());
        System.out.println("user pwd: "+user.getuserPassword());
         userService.insertOne(user, response);

    }
    @PostMapping("/payByAccountNumber")
    public boolean payByAccountNumber(@RequestBody PayByAccountNumberReq req, @RequestParam("userID") 
    int userID, HttpServletResponse response){
        return userService.payByAccountNumber(req.getDestAcc(), req.getBsbNumber(), req.getAmount(),
         req.getReason(), userID, response);
    }


    @PostMapping("/payByPayID")
    public boolean payByPayID(@RequestBody PayByPayIDReq req, @RequestParam("userID") int userID, 
     HttpServletResponse response){
       System.out.println("payId: "+req.getPayId());
        return userService.payByPayID(req.getPayId(), req.getAmount(), userID, response);
    }

    @PostMapping("/payBill")
    public boolean payBill(@RequestBody PayBillReq req, @RequestParam("userID") int userID,
      HttpServletResponse response){
        return userService.payBill(req.getReferenceNumber(), req.getBillerCode(),
         req.getAmount(), req.getNickname(), userID, response);
    }

     @GetMapping("/getAccountMoney")
    public double getAccountMoney( @RequestParam("userID") int userID, HttpServletResponse response){
        return userService.getAccountMoneyFunction(userID, response);
    }

     @GetMapping("/getTotalDebit")
    public double getTotalDebit( @RequestParam("userID") int userID, HttpServletResponse response){
        System.out.println("I am in userController of getTotalDebit");
        return userService.getTotalDebit(userID, response);
    }


     @GetMapping("/getTotalCredit")
    public double getTotalCredit( @RequestParam("userID") int userID, HttpServletResponse response){
        return userService.getTotalCredit(userID, response);
    }

     @GetMapping("/getLastDebit")
    public double getLastDebit( @RequestParam("userID") int userID, HttpServletResponse response){
        return userService.getLastDebit(userID, response);
    }

     @GetMapping("/getLastCredit")
    public double getLastCredit( @RequestParam("userID") int userID, HttpServletResponse response){
        return userService.getLastCredit(userID, response);
    }

    @GetMapping("/getAllTransactions")
    public List<MyTransaction> getAllTransactions(@RequestParam int userID, HttpServletResponse response) {
        return userService.getAllTransactions(userID, response);
    }


    @GetMapping("/getProfile")
    public UserProfile getProfile(@RequestParam int userID, HttpServletResponse response) {
        System.out.println("userID is: "+userID);
        return userService.getProfile(userID, response);
    }


    @GetMapping("/cardActive")
    public boolean getActiveStatus(@RequestParam int userID, HttpServletResponse response) {
        boolean active = userService.getActiveStatus(userID, response);
        System.out.println("active is: "+active);
        return active;

    }


    
    @GetMapping("/cardBlocked")
    public boolean getBlockedStatus(@RequestParam int userID, HttpServletResponse response) {
        boolean blocked = userService.getBlockedStatus(userID, response);
        System.out.println("blocked is: "+blocked);
        return blocked;

    }


    @PutMapping("/updateProfile")
    public boolean getProfile(@RequestBody UpdatedProfile u, @RequestParam int userID, HttpServletResponse response) {
        return userService.getProfile(u.getFirstName(), u.getMobileNumber(), u.getEmail(), u.getDateOfBirth(),
        u.getPassword(), userID, response);
    }



    @PutMapping("/blockCard")
    public boolean blockCard( @RequestParam int userID, HttpServletResponse response) {
        return userService.blockCard(userID, response);
    }


    @PutMapping("/unblockCard")
    public boolean unblockCard( @RequestParam int userID, HttpServletResponse response) {
        return userService.unblockCard(userID, response);
    }



    @PostMapping("/verifyCard")
    public boolean verifyCard(@RequestBody VerifyCard v, @RequestParam int userID, HttpServletResponse response) {
        System.out.println("ecp at ctrl is: "+v.getExpirationDate());
                System.out.println("number at ctrl is: "+v.getNumber());

        return userService.verifyCard(v.getId(), v.getNumber(), v.getExpirationDate(), userID, response);
    }

     @PostMapping("/chat")
    public String chatWithBot(@RequestBody Message req, @RequestParam("userID") int userID, HttpServletResponse response) {
        // String msg = convertJsonToString(userMessage);
        // System.out.println("userMessage is: "+userMessage);
        System.out.println("msg is: "+req.getUserMessage());
        String userMessage = req.getUserMessage();
        // return req.getUserMessage();
        String botResponse = chatbot.getBotResponse(userMessage);
        System.out.println("response: " + botResponse);
        if(botResponse.equals("ERR: No Reply Matched")){
            System.out.println("error");
        } else{
        boolean saved = userService.insertChat(userMessage, botResponse, userID, response);
        }
        return botResponse;
    }




    @PostMapping("/insertChat")
    public boolean insertChat(@RequestBody UserMessageReq message, @RequestParam("userID") int userID, HttpServletResponse response) {
    System.out.println("userID in insertChat is: " + userID);
    return userService.insertChat(message.getUserMessage(), message.getAnswer(), userID, response);
}


@GetMapping("/getAllMessages")
    public List<UserMessage> getAllMessages(@RequestParam("userID") int userID, HttpServletResponse response) {
    System.out.println("userID in getAllMessages is: " + userID);
    return userService.getAllMessages( userID, response);
}


    //query  transfer  save  withdraw
    //query
    

    //transfer
    // @PostMapping("/transferAccount")
    // public Result transferAccount (@RequestParam("accountMoney") double accountMoney ,
    //                                @RequestParam("targetAccount") int targetAccount , HttpServletRequest request){
    //     return Results.successWithData(userService.transferAccount(accountMoney , targetAccount , request) , BaseEnums.SUCCESS.code()) ;

    // }

    // //save
    // @PostMapping("/saveMoney")
    // public Result saveMoney (@RequestParam("accountMoney") Double accountMoney , HttpServletRequest request){
    //     return  Results.successWithData(userService.saveMoney(accountMoney , request) , BaseEnums.SUCCESS.code()) ;
    // }

    // //withdraw
    // @PostMapping("withdrawMoney")
    // public Result withdrawMoney (@RequestParam("accountMoney") Double accountMoney , HttpServletRequest request){
    //     return  Results.successWithData(userService.withdrawMoney(accountMoney , request) , BaseEnums.SUCCESS.code()) ;
    // }


    @PostMapping("/signup")
public Result signup(@RequestBody User user) {
    // Implement user registration logic here
    Result result = userService.signup(user);
    return result;
}



}
