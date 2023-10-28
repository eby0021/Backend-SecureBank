package select.system.dto;
public class PayByAccountNumberReq {
    private int destAcc;
    private int bsbNumber;
    private double amount;
    public int getDestAcc(){
        return destAcc;
    }
    public int getBsbNumber(){
        return bsbNumber;
    }
    public double getAmount() {
        return amount;
    }

    public void setDestAcc(int destAcc){
        this.destAcc = destAcc;
    }
    public void setBsbNumber(int bsbNumber) {
        this.bsbNumber = bsbNumber;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
