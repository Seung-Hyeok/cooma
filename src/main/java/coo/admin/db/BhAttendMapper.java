package coo.admin.db;

import java.util.ArrayList;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BhAttendMapper {

//일일 등원 리스트 교육별
	//대형견 => 교육없음, 공격성, 배변, 분리불안
	ArrayList<BhAttendReserDTO> dayEduNotListBig(BhAttendReserDTO reser);
	ArrayList<BhAttendReserDTO> dayEduRageListBig(BhAttendReserDTO reser);
	ArrayList<BhAttendReserDTO> dayEduPoopListBig(BhAttendReserDTO reser);
	ArrayList<BhAttendReserDTO> dayEduAnxietyListBig(BhAttendReserDTO reser);
	//중소형견 => 교육없음, 공격성, 배변, 분리불안
	ArrayList<BhAttendReserDTO> dayEduNotListSmall(BhAttendReserDTO reser);
	ArrayList<BhAttendReserDTO> dayEduRageListSmall(BhAttendReserDTO reser);
	ArrayList<BhAttendReserDTO> dayEduPoopListSmall(BhAttendReserDTO reser);
	ArrayList<BhAttendReserDTO> dayEduAnxietyListSmall(BhAttendReserDTO reser);
	
	//출석부 정보 
	List<BhAttendReserDTO> dayListBig(BhAttendReserDTO reser);
	List<BhAttendReserDTO> dayListSmall(BhAttendReserDTO reser);
	//reque 정보 가져오기
	String getRequeNote(BhAttendReserDTO reser);
	//대형견반 출석 수
	int bhCountAttBig(BhAttendReserDTO reser);
	int bhCountRealBig(BhAttendReserDTO reser);
	//중/소형견반 출석 수
	int bhCountAttSmall(BhAttendReserDTO reser);
	int bhCountRealSmall(BhAttendReserDTO reser);
	
	//등원
	int checkAttendTime(int todayNo);
	//하원
	int checkGoHomeTime(int todayNo);
	BhAttendReserDTO bhChkTime(int todayNo);
	
	//하원 지각 패널티
	void bhAddPanelty(BhAttendReserDTO addPanelty);

	//일일메모
	BhAttendReserDTO bhAttMemo(BhAttendReserDTO reser);
	String bhDogImg(BhAttendReserDTO reser);
	
	//일일메모수정
	int bhAttMemoModi(BhAttendReserDTO reser);
	
	//강아지의 이용권 별 출석 등 정보
	BhAttendReserDTO bhReserData(BhAttendReserDTO reser);
	List<BhAttendReserDTO> bhAttYes(BhAttendReserDTO getDaybydayData);
	List<BhAttendReserDTO> bhAttNo(BhAttendReserDTO getDaybydayData);
	List<BhAttendReserDTO> bhAttNotyet(BhAttendReserDTO getDaybydayData);
	BhAttendReserDTO bhAttTot(BhAttendReserDTO getDaybydayData);
	
	
	
	
}
