package coo.admin.db;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.ibatis.type.Alias;
import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Alias("nDTO")
@Data
public class ShNoticeDTO {
	int num, cnt;
	String title, content, photoFile, id;
	Date reg_date;
	MultipartFile noticeImg;
	
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy년 MM월 dd일");
	
	public String date() {
		String pp = sdf.format(reg_date);
		return pp;
	}
	

}