package select.system.dto;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;

@Entity
@Table(name = "user_messages")
public class UserMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "messageID")
    private int messageID;

    @Column(name = "userID")
    private int userID;

    @Column(name = "userMessage", nullable = false)
    private String userMessage;

    @Column(name = "answer", nullable = false)
    private String answer;

    public UserMessage() {
        // Default constructor
    }

    public UserMessage(int messageID, int userID, String userMessage, String answer) {
        this.messageID = messageID;
        this.userID = userID;
        this.userMessage = userMessage;
        this.answer = answer;
    }

    public int getMessageID() {
        return messageID;
    }

    public void setMessageID(int messageID) {
        this.messageID = messageID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getUserMessage() {
        return userMessage;
    }

    public void setUserMessage(String userMessage) {
        this.userMessage = userMessage;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    @Override
    public String toString() {
        return "UserMessage{" +
                "messageID=" + messageID +
                ", userID=" + userID +
                ", userMessage='" + userMessage + '\'' +
                ", answer='" + answer + '\'' +
                '}';
    }
}
