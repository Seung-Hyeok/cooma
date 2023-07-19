package coo.user.db;


import java.text.SimpleDateFormat;
import java.util.Date;

import coo.user.model.HmPData;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class HmQnaDTO {
	
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	
	@Resource
	public HmPData hp;
	
	 int no;
	 
	 int ano;
	 
	 @NotEmpty(message = "제목을 입력해주세요")
	 @Size(max = 15, message = "제목은 15글자 이하로 입력해주세요")
	 String title;
	 
	 @NotEmpty(message = "문의내용을 입력해주세요")
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
	 
	public String getContentBr() {
		return content.replaceAll("\n", "<br/>"); //여기서 줄바꿈으로 출력될수있게 한다
	}
	public String getAnswerBr() {
		return getAnswer.replaceAll("\n", "<br/>"); //여기서 줄바꿈으로 출력될수있게 한다
	}
}
