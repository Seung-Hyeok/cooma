package coo.admin.db;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.ibatis.type.Alias;

import lombok.Data;

@Alias("bhqDTO")
@Data
public class BhQnADTO {
	/*
	CREATE TABLE qna (
    no int auto_increment primary key, -- 번호
    title varchar(100) , -- 제목
    content text, -- 내용
    pid varchar(100) , -- 질문 작성자 id
    cnt int(11) , -- 조회수
    reg_date datetime, -- 질문 등록일
    getAnswer varchar(999), -- 관리자가 쓴 답변 (null 아니면 삭제 못하게 )
    answerDate datetime , -- 답변 등록일
    adminId varchar(100) -- 답변 작성한 관리자 id
	);
	*/
	Integer no, cnt, nowPage;
	String title, content, pid, getAnswer, adminId, kind, sch;
	Date reg_date, answerDate;
	
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm"); // 질문, 답변 등록일 포맷
	
	public String reg_dateStr() { //질문등록일
		String rd = sdf.format(reg_date);
		return rd;
	}
	
	public String answerDateStr() { //답변등록일
		String ad = sdf.format(answerDate);
		return ad;
	}
	
	public String AnsOrNot() {
		String answer = "답변대기중";
		if(getAnswer != null) {
			answer = "답변완료";
		}
		return answer;
	}
}
