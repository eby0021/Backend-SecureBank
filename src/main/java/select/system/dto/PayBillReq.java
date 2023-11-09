package select.system.dto;
public class PayBillReq {
    private int referenceNumber;
    private int billerCode;
    private double amount;
    private String nickname;

    public int getReferenceNumber() {
        return referenceNumber;
    }

    public void setReferenceNumber(int referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    public int getBillerCode() {
        return billerCode;
    }

    public void setBillerCode(int billerCode) {
        this.billerCode = billerCode;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
