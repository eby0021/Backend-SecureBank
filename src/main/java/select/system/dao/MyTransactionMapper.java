package select.system.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import select.system.dto.MyTransaction;
import select.system.dto.User;
import java.util.Date;

import java.util.List;

@Repository
@Mapper
public interface MyTransactionMapper {

    public MyTransaction selectById(int id);
    
    public List<MyTransaction> selectByAccountNumber(int accountNumber);

    // public boolean insertOnePayment(int sender_accountNumber, int receiver_accountNumber, double amount,
    //  String reason, Date transDate) ;

     public boolean insertOne(MyTransaction myTransaction);
    
    public boolean insertOneBill(MyTransaction myTransaction) ;

    public double selectLastDebitAmount(int accountNumber);
    
    public double selectLastCreditAmount(int accountNumber);
    
    
    public double selectTotalCreditAmount(int accountNumber);

    
    public double selectTotalDebitAmount(int accountNumber);



    // Define other methods for working with MyTransaction as needed
}
