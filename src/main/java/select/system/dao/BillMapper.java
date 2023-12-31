package select.system.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import select.system.dto.Bill;

import java.util.List;

@Repository
@Mapper
public interface BillMapper {

    public Bill selectByReferenceNumber(int referenceNumber);

    public List<Bill> selectAll();

    public boolean insertOne(int billerCode, int referenceNumber, double amount, String nickname, boolean isPaid);

    // Define other methods for working with Bill as needed
}
