package coo.admin.db;

import java.util.Date;

import org.apache.ibatis.type.Alias;
import lombok.Data;

@Alias("bhAtDTO") //별칭
@Data
public class BhAttendReserDTO {
	/*
	  pid VARCHAR(255),
	  dname VARCHAR(255),
	  reserNo INT auto_increment primary key,
	  pay INT default 0, -- 이용권금액
	  eduFee INT default 0, -- 교육금액
	  totFee int default 0,  -- 전체금액
	  edu VARCHAR(255), -- 훈련 : 배변 2900, 분리불안 4900, 공격성 5900, 훈련없음 0
	  reque TEXT, -- 요청사항
	  kind VARCHAR(255), -- 예약종류 : 정기권, 일일권
	  gap int default 0, -- 0:일일권 14:2주권 28:1개월 84:3개월
	  dogsize VARCHAR(255), -- 대형견, 중/소형견
	  payD datetime, -- 결제일 예약폼작성일
	  startD DATE, -- 등원 시작 종료
	  endD DATE,
	  weeks VARCHAR(255), -- 월화수목금, 월수금, 화목
	  bank VARCHAR(255),
	  account VARCHAR(255),
	  refund INT default 0,
	  refundD datetime -- 환불일자
	 */
	String pid, dname, edu, reque, kind, dogsize, weeks, bank, account;
	Date payD, startD, endD, refundD, today = new Date();
	Integer reserNo, pay, eduFee, totFee, gap, refund;
	
	
}
