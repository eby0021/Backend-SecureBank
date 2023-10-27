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

    public boolean insertOne(Card card);

    // Define other methods for working with Card as needed
}
