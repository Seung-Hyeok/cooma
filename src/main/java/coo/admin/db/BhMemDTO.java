package coo.admin.db;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import org.apache.ibatis.type.Alias;
import lombok.Data;

@Alias("bhmDTO") //별칭은 마이바티스에서 사용할 수 있지만 thymeleaf에서는 사용하지 못한다.
@Data
public class BhMemDTO {
	/*
	CREATE TABLE member (
	  pid VARCHAR(255) primary key,
	  pw VARCHAR(255),
	  pname VARCHAR(255),
	  tel VARCHAR(255),
	  birth DATE,
	  reg_date DATE,
	  addr1 VARCHAR(255),
	  addr2 VARCHAR(255),
	  email VARCHAR(255),
	  grade VARCHAR(255),
	  dog1 VARCHAR(255),
	  dog2 VARCHAR(255),
	  dog3 VARCHAR(255)
	);
	*/
	
	//dname => 관리자가 애견정보를 삭제했을때 회원 테이블 칼럼에서도 삭제되게 하기 위해 추가
	String pid, pw, pname, tel, addr1, addr2, email, grade, dog1, dog2, dog3, kind, sch, dname;
	Date birth, reg_date, today = new Date();
	Integer dogCnt, nowPage;
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy년 MM월 dd일");
	
	public int dogTotalCnt() {
		int i = 3;
		if(dog1==null || dog1.equals("")) { i -= 1; }
		if(dog2==null || dog2.equals("")) { i -= 1; }
		if(dog3==null || dog3.equals("")) { i -= 1; }
		return i; //dogCnt = 
	}

	public BhMemDTO() {
		super();
	}
	
	public String birthStr() {
		String dayStr = sdf.format(birth);
		return dayStr;
	}
	
	public String reg_dateStr() {
		String dayStr = sdf.format(reg_date);
		return dayStr;
	}
	
}
