package select.system.dto;
public class PayByPayIDReq {
    private int payID;
    private double amount;
    private String reason;
    public int getPayID(){
        return payID;
    }
    public double getAmount() {
        return amount;
    }
    public void setPayID(int destAcc){
        this.payID = payID;
    }
    public void setAmount(double amount) {
        this.amount = amount;
    }
    public String getReason(){
        return reason;
    }
    public void setReason(String reason){
        this.reason = reason;
    }
}
