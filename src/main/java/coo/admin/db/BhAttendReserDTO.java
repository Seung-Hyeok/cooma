package coo.admin.db;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.type.Alias;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Alias("bhAtDTO") //별칭
@Data
public class BhAttendReserDTO {
	/*
	 예약 테이블
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
	  
    create table daybyday (
	todayNo int auto_increment primary key, -- 일일번호
	reserNo int, -- 예약번호
	oneDay date, -- 일일날짜
	dname VARCHAR(255),
	kind VARCHAR(255), -- 일일권, 정기권
	dogsize VARCHAR(255),
	edu VARCHAR(255),
	pid VARCHAR(255),
	attendTime datetime,
	goHome datetime,
	penalty int default 0, -- 19시 이후 1시간마다 만원
	memo text
	 */
//daybyday 테이블 칼럼(reser와 겹치는 칼럼명 제외)
	String memo;
	Integer todayNo, penalty, timeGap;
	Date oneDay, attendTime, goHome; 
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	Date schDate; //schDate:출석부 검색 날짜값 저장 변수
	
//reser 테이블 칼럼
	String pid, dname, edu, reque, kind, dogsize, weeks, bank, account;
	Date payD, startD, endD, refundD, today = new Date(123, 6, 7); //today = new Date();
	Integer reserNo, pay, eduFee, totFee, gap, refund;
	
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy년 MM월 dd일 EEE");
	SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy년 MM월 dd일");
	SimpleDateFormat sdf2 = new SimpleDateFormat("hh:mm:ss");
	
//오늘 등원 리스트 상단 날짜 표시
	//Calendar calendar = Calendar.getInstance(); //오늘 요일 번호 알아내는 코드
	//int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK); // 1(일요일)부터 7(토요일)까지의 값
	int dayOfWeek = 6;
	String dayStr = "일월화수목금토".substring(dayOfWeek-1, dayOfWeek);
	
	public String todayStr() {
		String todayStr = sdf.format(today); // today => 포맷팅할 날짜 객체
		return todayStr;
	}
	
	public String schDate() {
		String schDate = sdf.format(this.schDate); 
		return schDate;
	}
//애견디테일 예약날짜 표시
	public String startDStr() {
		String startDStr = sdf1.format(startD); 
		return startDStr;
	}
	public String endDStr() {
		String endDStr = sdf1.format(endD); 
		return endDStr;
	}
	public String getTerm() {
		String term = "1일";
		if(gap == 84) { term = "3개월"; }
		if(gap == 28) { term = "1개월"; }
		if(gap == 14) { term = "2주"; }
		return term;
	}
	
	public String attendTime() {
		String attendTime = "";
		if(this.attendTime!=null) {
			attendTime = sdf2.format(this.attendTime);
		}
		return attendTime;
	}
	
	public String goHome() {
		String goHome = "";
		if(this.goHome!=null) {
			goHome = sdf2.format(this.goHome);
		}
		return goHome;
	}
	
//달력만들기
	//startD endD
	Calendar calendar = Calendar.getInstance();
	//setDate(1)
	public int stMonth() {
		calendar.setTime(startD);
		int stMonth = calendar.get(Calendar.MONTH)+1;
		return stMonth;
	}
	public int stDay() {
		calendar.setTime(startD);
		int stDay = calendar.get(Calendar.DAY_OF_MONTH);
		return stDay;
	}
	
	public int enMonth() {
		calendar.setTime(endD);
		int enMonth = calendar.get(Calendar.MONTH)+1;
		return enMonth;
	}
	public int enDay() {
		calendar.setTime(endD);
		int enDay = calendar.get(Calendar.DAY_OF_MONTH);
		return enDay;
	}
	
	public List<Integer> gapMonth() {
		List<Integer> months = new ArrayList<>();
	    for (int i = stMonth(); i <= enMonth(); i++) {
	        months.add(i);
	    }
		return months;
	}
	
}
