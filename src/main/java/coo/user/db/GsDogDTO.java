package coo.user.db;

import org.apache.ibatis.type.Alias;

import lombok.Data;

@Alias("gsdDTO")
@Data
public class GsDogDTO {
	String dname,gender,breed,photo,pid,notes,grade,dogsize;
	int dmonth,dyear;
	double weight;
}
