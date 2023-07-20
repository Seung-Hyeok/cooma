package coo.user.db;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.ibatis.type.Alias;

import lombok.Data;

@Alias("gsrDTO")
@Data
public class GsReserDTO {
	String pid,dname,edu,reque,kind,dogsize,weeks,bank,account;
	Integer reserNo,pay,refund,gap,totFee,eduFee;
	String payD,startD,endD,attendTime,goHome;
	Date oneDay;
	boolean refundChk;
	
	public String gap() {

        if(this.gap == 84) {
            return "3개월권 (84일)";
        }else if(this.gap == 28) {
            return "1개월권 (28일)";
        }else if(this.gap == 14) {
            return "2주권 (14일)";
        }else if(this.gap == 0) {
            return "1일";
        }else {
            return "aaaa";
        }

    }
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	SimpleDateFormat sdfYM = new SimpleDateFormat("yyyy-MM");
	SimpleDateFormat sdfD = new SimpleDateFormat("d");
	SimpleDateFormat sdfStr = new SimpleDateFormat("yyyy년 MM월 dd일");
	public boolean startDate() {
		Date startDate=null;
		Date currentDate =null;
		try {
			startDate = sdf.parse(startD);
			currentDate = new Date();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return startDate.before(currentDate);
	}
	public String oneDayStr() {
		String oneStr = sdf.format(oneDay);
		return oneStr;
	}
	public String oneYM() {
		String oneStr = sdfYM.format(oneDay);
		return oneStr;
	}
	public String getOneD() {
		String oneStr = sdfD.format(oneDay);
		return oneStr;
	}
	
	public String attendTimeStr() {
		if(attendTime==null) {
			return "";
		}
		return attendTime.split(" ")[1].substring(0, 5);
	}
	public String goHomeStr() {
		if(goHome==null) {
			return "";
		}
		return goHome.split(" ")[1].substring(0, 5);
	}
	
	DecimalFormat df = new DecimalFormat("#,###");
	
	public String payStr() {
		String pp = df.format(pay);
		return pp+'원';
	}
	
	public String refundStr() {
		if(refund == 0) {
			return "";
		}else {
			String pp = df.format(refund);
			return pp+'원';
		}
	}
	public String eduFeeStr() {
	
			String pp = df.format(eduFee);
			return pp+'원';
		
	}
	public String totFeeStr() {
		
			String pp = df.format(totFee);
			return pp+'원';
		
	}

	
	
}
