package coo.user.db;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;
@Data
public class HmFileData {
	MultipartFile dogimg;
	
	String birthstr;
	
	String email2;
	
	String tel1;
	String tel2;
	String tel3;
}
