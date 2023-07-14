package coo.user.model;


import org.apache.ibatis.type.Alias;

import lombok.Data;

@Alias("hP")
@Data
public class HmPData {

	Integer limit = 5, pageLimit=5, start, nowPage;
	Integer startPage, endPage, totalPage;
	String startD, endD, cate;
	

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


	public HmPData(Integer limit, Integer pageLimit ,Integer nowPage) {
		super();
		this.limit = limit;
		this.pageLimit = pageLimit;
		this.nowPage = nowPage;
		
	}
	
	
	
}
