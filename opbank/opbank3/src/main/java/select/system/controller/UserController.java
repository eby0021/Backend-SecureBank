package select.system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import select.base.Result;
import select.constants.BaseEnums;
import select.system.dto.User;
import select.system.dto.LoginRequest;
import select.system.service.UserService;
import select.util.PageBean;
import select.util.Results;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
@RestController
@RequestMapping("/sys/user")
public class UserController {

    @Autowired
    UserService userService ;

 
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



    // @GetMapping("/login")
    // public Result login(@RequestParam("email") String email, @RequestParam("password") String password){
    //     return Results.successWithData(userService.login(String email, String password) , BaseEnums.SUCCESS.code()) ;
    // }

    @PostMapping("/loginCheck")
    public Result loginCheck(@RequestBody LoginRequest loginRequest, HttpServletResponse response) {
        System.out.println("I am in userController");
        return Results.successWithData(userService.loginCheck(loginRequest.getEmail(), loginRequest.getPassword(), response), 
            BaseEnums.SUCCESS.code());
    }
    
    
@PostMapping("/insertOne")
    public Result insertOne(@RequestBody User user){
        return Results.successWithData(userService.insertOne(user) , BaseEnums.SUCCESS.code()) ;

    }
    //query  transfer  save  withdraw
    //query
    

    //transfer
    @PostMapping("/transferAccount")
    public Result transferAccount (@RequestParam("accountMoney") double accountMoney ,
                                   @RequestParam("targetAccount") int targetAccount , HttpServletRequest request){
        return Results.successWithData(userService.transferAccount(accountMoney , targetAccount , request) , BaseEnums.SUCCESS.code()) ;

    }

    //save
    @PostMapping("/saveMoney")
    public Result saveMoney (@RequestParam("accountMoney") Double accountMoney , HttpServletRequest request){
        return  Results.successWithData(userService.saveMoney(accountMoney , request) , BaseEnums.SUCCESS.code()) ;
    }

    //withdraw
    @PostMapping("withdrawMoney")
    public Result withdrawMoney (@RequestParam("accountMoney") Double accountMoney , HttpServletRequest request){
        return  Results.successWithData(userService.withdrawMoney(accountMoney , request) , BaseEnums.SUCCESS.code()) ;
    }


    @PostMapping("/signup")
public Result signup(@RequestBody User user) {
    // Implement user registration logic here
    Result result = userService.signup(user);
    return result;
}



}
