package select.system.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import java.util.List;
import select.system.dto.UserMessage;

@Repository
@Mapper
public interface UserMessageMapper {

    public UserMessage selectById(int messageID);

    public List<UserMessage> getAllMessages(int userID);

    public boolean insertMessage(String userMessage, String answer, int userID);

    // Add other methods as needed
}
