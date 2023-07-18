package coo.user.db;

import java.util.Date;

import lombok.Data;

@Data
public class HmMemberDTO {
	String pid;
	
	String pw;
	
	String pw1;
	
	String pname;
	
	String tel;
	
	Date birth;
	
	Date reg_date;
	
	String addr1;
	
	String addr2;
	
	String email;
	
	String grade;
	
	String dog1;
	
	String dog2;
	
	String dog3;
	
	String pwquestion;
	
	String pwanswer;
	
	Date today = new Date();
}
