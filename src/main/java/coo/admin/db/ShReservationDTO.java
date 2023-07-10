package coo.admin.db;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.ibatis.type.Alias;

import lombok.Data;

@Alias("rDTO")
@Data
public class ShReservationDTO {
	
	String pid, dname, edu, reque, kind, dogsize, weeks, bank, account, sch, month;
	Integer reserNo, pay, refund, cnt, eduFee, totFee, gap, amount;
	Date payD, startD, endD, refundD;
	
	
	public ShReservationDTO(String month, Integer amount) {
		super();
		this.month = month;
		this.amount = amount;
	}
	
	public ShReservationDTO() {
		
	}
	
	
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // 결제일 포맷
	SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd"); // 시작,종료일 포맷
	
	// 결제일
	public String pDate() {
		
		String pd = sdf.format(payD);
		return pd;
	}
	// 시작일
	public String sDate() {
		
		String pd = sdf2.format(startD);
		return pd;
	}
	// 종료일
	public String eDate() {
		
		String pd = sdf2.format(endD);
		return pd;
	}
	// 환불일자
	public String rDate() {
		
		String pd = sdf2.format(refundD);
		return pd;
	}
	
	DecimalFormat df = new DecimalFormat("#,###");
	
	public String pay() {
		String pp = df.format(pay);
		return pp;
	}
	
	public String refund() {
		if(refund == 0) {
			return "";
		}else {
			String pp = df.format(refund);
			return pp;
		}
	}
	
	public String epay() {
		
		
		String pp = df.format(eduFee);
		return pp;
	}
	
	public String tpay() {
		String pp = df.format(totFee);
		return pp;
	}
	
	public String gap() {
		
		if(this.gap == 84) {
			return "3개월";
		}else if(this.gap == 28) {
			return "1개월";
		}else if(this.gap == 14) {
			return "2주";
		}else if(this.gap == 0) {
			return "1일";
		}else {
			return "";
		}
		
	}
	
	public boolean state() {
		
		Date today = new Date();
		
		if(today.compareTo(startD) <= 0) {
			return true;
		}else {
			return false;
		}
	
	}
	
	

}
