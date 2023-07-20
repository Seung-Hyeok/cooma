package coo.admin.db;

import java.util.Calendar;
import java.util.Date;

import org.apache.ibatis.type.Alias;
import lombok.Data;

@Alias("bhdDTO")
@Data
public class BhDogsDTO {
	/*
	dname VARCHAR(255),
	weight DOUBLE,
	dogsize varchar(255),
	gender VARCHAR(255), -- 암컷, 수컷
	breed VARCHAR(255), -- 견종이다
	dyear int, -- 연
	dmonth int, -- 월
	photo VARCHAR(255),
	pid VARCHAR(255), -- 회원아이디
	notes TEXT, -- 특이사항
	grade VARCHAR(255)
	*/
	
	//pname 리스트에 표시하기 위해 추가
	//sch 검색을 위해 추가
	String dname, dogsize, gender, breed, photo, pid, notes, grade, adminMemo;
	String kind, sch, pname;
	Integer weight, dyear, dmonth, nowPage;
	

	public String memoChk() {
		String qus = "";
		if(adminMemo != null) {
			qus = "○";
		}
		return qus;
	}
	
	Date today = new Date();
	Calendar calendar = Calendar.getInstance();

	public String dogBirthStr() {
		calendar.setTime(today);
		int thisTotM = calendar.get(Calendar.YEAR)*12+calendar.get(Calendar.MONTH);
		int birthTotM = dyear*12+dmonth;
		int getY = (thisTotM-birthTotM)/12;
		int getM = (thisTotM-birthTotM)-getY*12;
		return getY+"년 "+getM+"개월 생";
	}
	
	String blackMemo;
	
	public String blackMemoBr() {
		return blackMemo.replaceAll("\n", "<br/>"); //여기서 줄바꿈으로 출력될수있게 한다
	}
}
