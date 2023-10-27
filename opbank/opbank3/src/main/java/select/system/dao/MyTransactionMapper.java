package select.system.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import select.system.dto.MyTransaction;

@Repository
@Mapper
public interface MyTransactionMapper {

    public MyTransaction selectById(int id);

    // Define other methods for working with MyTransaction as needed
}
