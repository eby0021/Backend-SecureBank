// chatbot/RiveScriptChatbot.java
package select.system.chatbot;

import com.rivescript.RiveScript;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class RiveScriptChatbot {

    private static final String BOT_NAME = "SecureBankBot"; // Replace with a unique name for your bot

    private RiveScript bot;

    public RiveScriptChatbot() {
        bot = new RiveScript();
        try {
            PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
            Resource[] resources = resolver.getResources("classpath*:rivescript_files/**");

            for (Resource resource : resources) {
                try {
                    String filePath = resource.getFile().getPath();
                    bot.loadFile(filePath);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            bot.sortReplies();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public String getBotResponse(String userMessage) {
         this.bot.sortReplies();
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