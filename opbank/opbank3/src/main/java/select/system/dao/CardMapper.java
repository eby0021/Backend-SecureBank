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

    // Define other methods for working with Card as needed
}
