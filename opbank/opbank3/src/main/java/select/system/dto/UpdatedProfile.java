package select.system.dto;

import java.util.Date;

public class UpdatedProfile {
    private String firstName;
    private String mobileNumber;
    private String email;
    private Date dateOfBirth;
    private String userPassword;

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

    public String getPassword() {
        return userPassword;
    }

    public void setPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public UpdatedProfile(String firstName, String mobileNumber, String email, Date dateOfBirth, String userPassword) {
        this.firstName = firstName;
        this.mobileNumber = mobileNumber;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.userPassword = userPassword;
    }

}
