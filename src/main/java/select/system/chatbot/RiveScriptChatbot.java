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
            // 使用 PathMatchingResourcePatternResolver 加载匹配的资源
            PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
            // 加载匹配的资源，使用 "classpath*:select/system/rivescript_files/**" 以确保在多个 Jar 包中也能正确加载
            Resource[] resources = resolver.getResources("classpath*:select/system/rivescript_files/**");

            for (Resource resource : resources) {
                // 获取相对于类路径的文件路径
                String relativePath = "classpath:" + resource.getURL().getPath();
                // 加载文件
                bot.loadFile(relativePath);
            }

            bot.sortReplies();
        } catch (Exception e) {
            // 处理异常
            e.printStackTrace();
        }
    }
    public String getBotResponse(String userMessage) {
        // this.bot.sortReplies();
      
        String response = bot.reply("console: ", userMessage);
        return response;
    }
}