package select.system.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import select.system.dto.Card;

import java.util.List;

@Repository
@Mapper
public interface CardMapper {

    public Card selectById(int id);

    public List<Card> selectAll();

    public boolean insertOne(int number, int userID, boolean is_blocked, boolean is_activated, String expiration_date);

    public Boolean getActiveStatus(int userID);

    public Boolean getBlockedStatus(int userID);

    public boolean activateCard(int userID);

    public boolean blockCard(int userID);

    public boolean unblockCard(int userID);


    // Define other methods for working with Card as needed
}
