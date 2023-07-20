package coo.user.db;


import java.util.Date;

import lombok.Data;

@Data
public class HmDogsDTO {
	
	String dname;
	
	double weight;
	
	String gender;
	
	String breed;
	
	String dogsize;
	
	int dyear;
	
	int dmonth;
	
	String photo;
	
	String pid;
	
	String notes;
	
	String grade;
	
	String blackMemo;
	
	public String blackMemoBr() {
		return blackMemo.replaceAll("\n", "<br/>"); //여기서 줄바꿈으로 출력될수있게 한다
	}
	
	Date today = new Date();
}
