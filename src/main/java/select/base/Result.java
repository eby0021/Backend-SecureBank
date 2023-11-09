package select.base;

import java.io.Serializable;

/**
 * Project: OP-Bank
 * Module ID:
 * Comments:
 * JDK version used: JDK 17
 * Namespace: std
 * Author： YeTian
 * Create Date： 8/10/2023
 * Modified By： None
 * Modified Date: None
 * Why & What is modified: None
 * Version: 0.1.0
 */
//Front-end return object
public class Result implements Serializable {
    private static final long serialVersionUID = 1430633339880116031L;

    //Success mark
    private boolean success = true ;

    //If the return status code is empty, the default is 200 and such as 403 404 500
    private Integer status ;

    //encode
    private String code ;

    //Related news
    private String msg ;

    //Relevant data
    private  Object data ;

    public Result(){}

    public Result(boolean success){
        this.success = success ;
    }
    public Result(boolean success , Integer status) {
        this.success = success ;
        this.status = status ;
    }
    public Result(boolean success , String code , String msg) {
        this.status = status ;
        this.success = success ;
        this.msg = msg ;
    }
    public Result(boolean success , Integer status , String msg , String code){
        this.success = success;
        this.msg = msg ;
        this.status = status ;
        this.code = code ;
    }
    public Result(Boolean success , String msg , String code , Object data){
        this.success = success ;
        this.code = code ;
        this.data = data ;
        this.msg = msg ;
    }
    public boolean isSuccess(){
        return success ;
    }
    public void setSuccess(boolean success){
        this.success = success ;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
