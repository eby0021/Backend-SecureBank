package select.base;


/**
 * Project: OP-Bank
 * Module ID:
 * Comments: Set the expiration and reset time for token
 * JDK version used: JDK 17
 * Namespace: std
 * Author： YeTian
 * Create Date： 8/10/2023
 * Modified By： None
 * Modified Date: None
 * Why & What is modified: None
 * Version: 0.1.0
 */
public class Constants {
    //redis storage token expiration time
    public static final Integer TOKEN_EXPIRED_TIME = 6000*2 ;
    //Set the token expiration time limit
    public static final Integer TOKEN_RESET_TIME = 1000000*100 ;
}
