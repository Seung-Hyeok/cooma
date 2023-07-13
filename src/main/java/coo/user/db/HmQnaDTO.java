package coo.user.db;


import java.text.SimpleDateFormat;
import java.util.Date;

import lombok.Data;

@Data
public class HmQnaDTO {
	
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	
	 int no;
	 
	 int ano;
	 
	 String title;
	 
	 String content;
	 
	 String pid;
	 
	 int cnt;
	 
	 Date reg_date;
	 
	 String getAnswer;
	 
	 Date answerDate;
	 
	 String adminId;
	 
	 String sch;
	 
	 public String rDate() {
			
		String pd = sdf.format(reg_date);
		return pd;
	}
}
