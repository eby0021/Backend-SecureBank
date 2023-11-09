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
    bot.loadDirectory("D:\\MERN Work\\Secure Bank\\opbank\\opbank3\\src\\main\\java\\select\\system\\chatbot\\rivescript_files");
      bot.sortReplies();
        // Initialize RiveScript bot
        // bot.setDebug(); // Use setDebug() without a boolean parameter
        // try{
        // bot.loadDirectory(new ClassPathResource("rivescript_files").getURL().getPath());
       // bot.loadDirectory(new ClassPathResource("rivescript_files").getURL().getPath());
      
        // }catch(Exception e){
        // }
    }

    public String getBotResponse(String userMessage) {
        // this.bot.sortReplies();
      
        String response = bot.reply("console: ", userMessage);
        return response;
    }
}