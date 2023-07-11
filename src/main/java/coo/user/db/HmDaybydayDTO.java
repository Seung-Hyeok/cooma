package coo.user.db;


import java.util.Date;

import lombok.Data;

@Data
public class HmDaybydayDTO {
	
	int todayNo;
	
	int reserNo;
	
	Date oneDay;
	
	String dname;
	
	String kind;
	
	String dogsize;
	
	String edu;
	
	String pid;
	
	Date attendTime;
	
	Date goHome;
	
	int penalty;
}
