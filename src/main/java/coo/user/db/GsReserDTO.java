package coo.user.db;

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
		return attendTime.split(" ")[1].substring(0, 5);
	}
	public String goHomeStr() {
		return goHome.split(" ")[1].substring(0, 5);
	}
}
