package select.util;


import select.base.Result;

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
public class Results {

    //Successful service call
    public static Result success(){
        return new Result() ;
    }
    public static Result success(String msg){
        return new Result(true , null , msg) ;
    }
    public static Result sucess(String code , String msg){
        return new Result(true , null , code , msg) ;
    }
    public static Result successWithData(Integer status){
        return new Result(true , status) ;
    }
    public static Result successWithData(Integer status , String msg){
        return new Result(true , status , null , msg) ;
    }
    public static Result successWithData(Object data){
        return new Result(true ,null , null ,  data) ;
    }
    public static Result successWithData(Object data , String msg){
        return new Result(true , null , msg , data)  ;
    }

    //Service call failure
    public static Result failure() {
        return new Result(false);
    }

    public static Result failure(String msg) {
        return new Result(false, null, msg);
    }

    public static Result failure(String code, String msg) {
        return new Result(false, code, msg);
    }

    public static Result failureWithStatus(Integer status) {
        return new Result(false, status);
    }

    public static Result failureWithStatus(Integer status, String msg) {
        return new Result(false, status, null, msg);
    }

    public static Result failureWithData(Object data) {
        return new Result(false, null, null, data);
    }

    public static Result failureWithData(Object data, String msg) {
        return new Result(false, null, msg, data);
    }

    public static Result failureWithData(Object data, String code, String msg) {
        return new Result(false, code, msg, data);
    }


}
