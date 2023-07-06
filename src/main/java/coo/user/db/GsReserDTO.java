package coo.user.db;

import java.util.Date;

import org.apache.ibatis.type.Alias;

import lombok.Data;

@Alias("gsrDTO")
@Data
public class GsReserDTO {
	String pid,dname,edu,reque,kind,dogsize,weeks,bank,account;
	Integer reserNo,pay,refund,gap;
	String payD,startD,endD;
	
	public String gap() {

        if(this.gap == 84) {
            return "3개월권";
        }else if(this.gap == 28) {
            return "1개월권";
        }else if(this.gap == 14) {
            return "2주권";
        }else if(this.gap == 0) {
            return "일일권";
        }else {
            return "aaaa";
        }

    }
}
