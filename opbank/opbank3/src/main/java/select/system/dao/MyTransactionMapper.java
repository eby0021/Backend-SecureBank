package select.system.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import select.system.dto.MyTransaction;
import select.system.dto.User;

import java.util.List;

@Repository
@Mapper
public interface MyTransactionMapper {

    public MyTransaction selectById(int id);
    
    public List<MyTransaction> selectByAccountNumber(int accountNumber);

    public boolean insertOne(MyTransaction myTransaction) ;
    
    public boolean insertOneBill(MyTransaction myTransaction) ;




    // Define other methods for working with MyTransaction as needed
}
