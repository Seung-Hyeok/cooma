package coo.admin.db;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import coo.admin.model.BhPData;

@Mapper
public interface BhDogMapper {
//리스트
	int bhDogsTotal(BhPData pd);
	List<BhDogsDTO> bhDogList(BhPData pd);
	//애견 주인 이름 list에 저장
	String bhFindMemName(BhDogsDTO dog);
	
//디테일
	BhDogsDTO bhDogDetail(BhDogsDTO dog);
	//강아지 주인에 대한 정보를 가져온다. (아래 코드와 중복인데 그냥한다)
	BhMemDTO bhDogsMem(BhDogsDTO dog);
	//선택한 강아지의 이전 예약내역을 확인한다.
	List<BhAttendReserDTO> bhDogsReser(BhDogsDTO dog);
//수정
	int bhDogGradeChk(BhDogsDTO dog);
	int bhDogModify(BhDogsDTO dog);
//삭제
	//삭제전 예약 여부 확인
	int bhDogBeforeDelete(BhDogsDTO dog);
	//강아지 삭제
	int bhDogDelete(BhDogsDTO dog);
	//강아지 정보 삭제시 BhDogsDTO를 사용해 회원정보를 가져오고
	BhMemDTO bhGetMemInform(BhDogsDTO dog);
	//회원 table에서 강아지 이름 삭제
	int bhDogsNameDelete(BhMemDTO mem);	
}
