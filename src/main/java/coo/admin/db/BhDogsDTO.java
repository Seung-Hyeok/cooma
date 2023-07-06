package coo.admin.db;

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
	Integer weight, dyear, dmonth;

	public String memoChk() {
		String qus = "";
		if(adminMemo != null) {
			qus = "○";
		}
		return qus;
	}
}
