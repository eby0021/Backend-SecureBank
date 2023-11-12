// chatbot/RiveScriptChatbot.java
package select.system.chatbot;

import com.rivescript.RiveScript;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Component;

@Component
public class RiveScriptChatbot {

    private static final String BOT_NAME = "SecureBankBot"; // Replace with a unique name for your bot

    private RiveScript bot;

    public RiveScriptChatbot() {
        bot = new RiveScript();
        try {
            PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
            Resource[] resources = resolver.getResources("classpath*:select/system/rivescript_files/**");

            for (Resource resource : resources) {
                String relativePath = "classpath:" + resource.getURL().getPath();
                bot.loadFile(relativePath);
            }

            bot.sortReplies();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public String getBotResponse(String userMessage) {
        // this.bot.sortReplies();
      
        String response = bot.reply("console: ", userMessage);
        return response;
    }
}