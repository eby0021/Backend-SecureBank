package select.system.dto;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "Bill")
public class Bill implements Serializable {

    @Id
    @Column(name = "referenceNumber")
    private int referenceNumber;

    @Column(name = "billerCode")
    private int billerCode;

    @Column(name = "amount", precision = 10, scale = 2)
    private double amount;

    @Column(name = "nickname", length = 255)
    private String nickname;

    @ManyToOne
    @JoinColumn(name = "billerCode", referencedColumnName = "userID")
    private User user;

    @Column(name = "isPaid")
    private boolean isPaid;

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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    public boolean isPaid() {
        return isPaid;
    }
    
    public void setPaid(boolean isPaid) {
        this.isPaid = isPaid;
    }
}
