// chatbot/RiveScriptChatbot.java
package select.system.chatbot;

import com.rivescript.RiveScript;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

@Component
public class RiveScriptChatbot {

    private static final String BOT_NAME = "SecureBankBot"; // Replace with a unique name for your bot

    private RiveScript bot;

    public RiveScriptChatbot() {
      bot = new RiveScript();
        String currentDirectory = System.getProperty("user.dir");
    bot.loadDirectory(currentDirectory+"\\src\\main\\java\\select\\system\\rivescript_files");
      bot.sortReplies();

    }

    public String getBotResponse(String userMessage) {
        // this.bot.sortReplies();
      
        String response = bot.reply("console: ", userMessage);
        return response;
    }
}