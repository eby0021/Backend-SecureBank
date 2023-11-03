package select.system.dto;

import java.util.Date;

public class UserProfile {
    private String firstName;
    private String mobileNumber;
    private String email;
    private Date dateOfBirth;
    private int accountNumber;
    private String userPassword;
    private int payId;

    // Getters and Setters for each attribute
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(int accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getPassword() {
        return userPassword;
    }

    public void setPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public int getPayId() {
        return payId;
    }

    public void setPayId(int payId) {
        this.payId = payId;
    }
    public UserProfile(String firstName, String mobileNumber, String email, Date dateOfBirth, int accountNumber, String userPassword, int payId) {
        this.firstName = firstName;
        this.mobileNumber = mobileNumber;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.accountNumber = accountNumber;
        this.userPassword = userPassword;
        this.payId = payId;
    }

}
