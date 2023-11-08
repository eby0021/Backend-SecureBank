package select.system.dto; // Replace with the actual package name

public class UserMessageReq {
    private String userMessage;
    private String answer;

    public UserMessageReq() {
        // Default constructor
    }

    public UserMessageReq(String userMessage, String answer) {
        this.userMessage = userMessage;
        this.answer = answer;
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

  
}

