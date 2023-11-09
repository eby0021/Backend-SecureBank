package select.system.dto;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "Account")
public class Account implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "accountNumber")
    private int accountNumber;

    @Column(name = "bsbNumber", nullable = false)
    private int bsbNumber;

    @Column(name = "amount")
    private double amount;

    @OneToOne
    @JoinColumn(name = "userID")
    private User user;

    public int getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(int accountNumber) {
        this.accountNumber = accountNumber;
    }

    public int getBsbNumber() {
        return bsbNumber;
    }

    public void setBsbNumber(int bsbNumber) {
        this.bsbNumber = bsbNumber;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
