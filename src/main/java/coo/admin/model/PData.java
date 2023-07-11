package coo.admin.model;

import org.apache.ibatis.type.Alias;

import lombok.Data;
import lombok.Getter;

@Alias("pD")
@Data
public class PData {

	Integer limit = 16, pageLimit=5, start, nowPage;
	Integer startPage, endPage, totalPage;
	String sch, startD, endD, cate;
	

	public void setTotal(int total) {
		totalPage = total / limit;
		if(total % limit != 0) {
			totalPage++;
		}
		start = (nowPage-1) * limit;
		startPage = (nowPage-1)/ pageLimit * pageLimit +1;
		endPage = startPage + pageLimit - 1;
		
		if(endPage>totalPage) {
			endPage = totalPage;
		}
	}
	
	
	
	
	
}
