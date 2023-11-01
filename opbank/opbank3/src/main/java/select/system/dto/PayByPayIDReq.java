package select.system.dto;
public class PayByPayIDReq {
    private int payId;
    private double amount;
    public int getPayId(){
        return payId;
    }
    public double getAmount() {
        return amount;
    }
    public void setPayId(int payId){
        this.payId = payId;
    }
    public void setAmount(double amount) {
        this.amount = amount;
    }
}
