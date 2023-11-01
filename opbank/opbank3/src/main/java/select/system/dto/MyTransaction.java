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

    @Column(name = "sender_accountNumber")
    private int sender_accountNumber;

    
    @Column(name = "receiver_accountNumber")
    private int receiver_accountNumber;

    @Column(name = "reason", length = 100)
    private String reason;

    @Column(name = "amount", precision = 10, scale = 3)
    private double amount;

    @Column(name = "transDate")
    private Date transDate;

    @Column(name ="referenceNumber")
    private int referenceNumber;
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getReferenceNumber() {
        return referenceNumber;
    }

    public void setReferenceNumber(int referenceNumber){
        this.referenceNumber = referenceNumber;
    }

    public int getSenderAccountNumber() {
        return sender_accountNumber;
    }

    public void setSenderAccountNumber(int sender_accountNumber) {
        this.sender_accountNumber = sender_accountNumber;
    }

    public int getReceiverAccountNumber() {
        return receiver_accountNumber;
    }

    public void setReceiverAccountNumber(int receiver_accountNumber) {
        this.receiver_accountNumber = receiver_accountNumber;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getTransDate() {
        return transDate;
    }

    public void setTransDate(Date transDate) {
        this.transDate = transDate;
    }

     public MyTransaction(int senderAccount, int receiverAccount, String reason, double amount) {
        this.sender_accountNumber = senderAccount;
        this.receiver_accountNumber = receiverAccount;
        this.reason = reason;
        this.amount = amount;
        this.transDate = new Date(); // Set the transDate to the current date and time
    }

     public MyTransaction(int senderAccount, double amount, int receiverAccount ) {
        this.sender_accountNumber = senderAccount;
        this.receiver_accountNumber = receiverAccount;
        this.amount = amount;
        this.transDate = new Date(); // Set the transDate to the current date and time
    }

     public MyTransaction(int senderAccount, int referenceNumber, double amount) {
        this.referenceNumber = referenceNumber;
        this.amount = amount;
        this.sender_accountNumber = senderAccount;
        this.transDate = new Date(); // Set the transDate to the current date and time
    }

    public MyTransaction(int id, int sender_accountNumber, int receiver_accountNumber, int referenceNumber, String reason, double amount, Date transDate) {
        this.id = id;
        this.sender_accountNumber = sender_accountNumber;
        this.receiver_accountNumber = receiver_accountNumber;
        this.referenceNumber = referenceNumber;
        this.reason = reason;
        this.amount = amount;
        this.transDate = transDate;
    }
    
}
