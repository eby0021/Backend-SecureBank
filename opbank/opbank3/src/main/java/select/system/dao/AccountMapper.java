package select.system.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import select.system.dto.Account;
import select.system.dto.User;

import java.util.List;

@Repository
@Mapper
public interface AccountMapper {

    public Account selectByAccountNumber(int accountNumber);

    public Account selectByUserID(int userID);

    public List<Account> selectAll();

    public boolean insertOne(int userID);

    public boolean saveMoney(int accountNumber, double amountToAdd);

    public boolean withdrawMoney(int accountNumber, double amountToAdd);


}
