package select.system.dto;

import javax.persistence.*;

@Entity
@Table(name = "Account")
public class Account {

    @Id
    @Column(name = "accountNumber")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int accountNumber;

    @Column(name = "bsbNumber", nullable = false)
    private String bsbNumber;

    @Column(name = "accountTitle", nullable = false)
    private String accountTitle;

    @Column(name = "amount")
    private double amount = 0.0; // Default value is set to 0.0

    @OneToOne(mappedBy = "account")
    private User user;

    // Constructor, getters, and setters for Account
}
