package select.system.dto;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "MyTransaction")
public class MyTransaction implements Serializable {

    @Id
    @Column(name = "id")
    private int id;

    @OneToOne
    @JoinColumn(name = "sender_accountNumber")
    private Account senderAccount;

    @ManyToOne
    @JoinColumn(name = "receiver_accountNumber")
    private Account receiverAccount;

    @Column(name = "reason", length = 100)
    private String reason;

    @Column(name = "amount", precision = 10, scale = 3)
    private BigDecimal amount;

    @Column(name = "transDate")
    private Date transDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Account getSenderAccount() {
        return senderAccount;
    }

    public void setSenderAccount(Account senderAccount) {
        this.senderAccount = senderAccount;
    }

    public Account getReceiverAccount() {
        return receiverAccount;
    }

    public void setReceiverAccount(Account receiverAccount) {
        this.receiverAccount = receiverAccount;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Date getTransDate() {
        return transDate;
    }

    public void setTransDate(Date transDate) {
        this.transDate = transDate;
    }
}
