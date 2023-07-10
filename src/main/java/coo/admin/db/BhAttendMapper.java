package coo.admin.db;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BhAttendMapper {
	List<BhAttendReserDTO> todayListBig(BhAttendReserDTO reser);
	List<BhAttendReserDTO> todayListSmall(BhAttendReserDTO reser);
	//reque 정보 가져오기
	String getRequeNote(BhAttendReserDTO reser);
	
	//등원
	int checkAttendTime(int todayNo);
	//하원
	int checkGoHomeTime(int todayNo);
	BhAttendReserDTO bhChkTime(int todayNo);
	
	void bhAddPanelty(BhAttendReserDTO addPanelty);
	
}
