package select.system.dto;
public class VerifyCard {
    private int id;
    private int number;
    private String expirationDate;

    // Constructors (if needed)
    public VerifyCard() {
    }

    public VerifyCard(int id, int number, String expirationDate) {
        this.id = id;
        this.number = number;
        this.expirationDate = expirationDate;
    }

    // Getters and Setters
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
}
