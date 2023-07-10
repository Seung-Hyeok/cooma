package coo.admin.model;

import org.apache.ibatis.type.Alias;

import lombok.Getter;

@Alias("pD")
@Getter
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
	
	public void setStartD(String startD) {
		this.startD = startD;
	}
	
	public void setEndD(String endD) {
		this.endD = endD;
	}


	public void setNowPage(int nowPage) {
		this.nowPage = nowPage;
	}


	public void setSch(String sch) {
		this.sch = sch;
	}
	
	public void setCate(String cate) {
		this.cate = cate;
	}
	
	
	
}
