package coo.user.db;

import java.util.Date;

import lombok.Data;

@Data
public class HmReserDTO {
	
  String pid;
  String dname;
  int reserNo; //auto_increment primary key
  int pay; //결제금액
  String edu; //훈련유무
  String reque; //요청사항
  String kind; //예약 타입 정기권 1일권 대형견 소형견
  String dogsize;
  Date payD; //결제일 예약폼작성일
  Date startD; //등원 시작 종료
  Date endD;
  String weeks; //등원 요일
  String bank;
  String account;
  int refund;
}
