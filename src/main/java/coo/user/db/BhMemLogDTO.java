package coo.user.db;

import java.util.Date;

import org.apache.ibatis.type.Alias;
import lombok.Data;

@Alias("bhlDTO")
@Data
public class BhMemLogDTO {

	String pid, pw, pname, tel, addr1, addr2, email, grade;
	String dog1, dog2, dog3;
	
	Date birth, reg_date;
	
}
