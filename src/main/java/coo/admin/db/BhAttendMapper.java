package coo.admin.db;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BhAttendMapper {
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
}
