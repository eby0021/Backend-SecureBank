// chatbot/RiveScriptChatbot.java
package select.system.chatbot;

import com.rivescript.RiveScript;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;

@Component
public class RiveScriptChatbot {

    private static final String BOT_NAME = "SecureBankBot"; // Replace with a unique name for your bot

    private RiveScript bot;

    public RiveScriptChatbot() {
        bot = new RiveScript();
        try {
            URL url = RiveScriptChatbot.class.getClassLoader().getResource("rivescript_files/example.rive");
            if (url != null) {
                try (InputStream inputStream = url.openStream()) {
                    bot.loadInputStream(inputStream);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            bot.sortReplies();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String readInputStream(InputStream inputStream) {
        try (Scanner scanner = new Scanner(inputStream).useDelimiter("\\A")) {
            return scanner.hasNext() ? scanner.next() : "";
        }
    }


    public String getBotResponse(String userMessage) {
        // this.bot.sortReplies();

        String response = bot.reply("console: ", userMessage);
        return response;
    }

    public static void main(String[] args) {
        RiveScriptChatbot chatbot = new RiveScriptChatbot();
        String userInput = "hello";
        String botResponse = chatbot.getBotResponse(userInput);
        System.out.println(botResponse);
    }
}