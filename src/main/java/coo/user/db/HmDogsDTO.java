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
	
	Date today = new Date();
}
