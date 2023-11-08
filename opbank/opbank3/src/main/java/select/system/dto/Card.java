package select.system.dto;
import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "Card")
public class Card implements Serializable {

    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "number", unique = true, length = 50)
    private int number;

    @Column(name = "expiration_date")
    private String expirationDate;

    @Column(name = "is_blocked")
    private boolean isBlocked;


    @Column(name = "is_activated")
    private boolean isActivated;

    @OneToOne
    @JoinColumn(name = "userID")
    private User user;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public boolean getIsBlocked() {
        return isBlocked;
    }

    public void setBlocked(boolean blocked) {
        isBlocked = blocked;
    }

     public boolean getIsActive() {
        return isActivated;
    }

    public void setActive(boolean isActivated) {
        isActivated = isActivated;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
