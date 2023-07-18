package coo.admin.db;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import coo.admin.model.BhPData;

@Mapper
public interface BhMemMapper {
//리스트
	int bhMemTotal(BhPData pd);
	List<BhMemDTO> bhMemList(BhPData pd);
//디테일
	BhMemDTO bhMemDetail(BhMemDTO mem);
	//강아지들
	List<BhDogsDTO> bhMemsDog(BhMemDTO mem);
//수정	
	int bhMemModify(BhMemDTO mem);
//삭제
	//삭제전 예약 확인
	int bhMemBeforeDelete(BhMemDTO mem);
	//회원 삭제
	int bhMemDelete(BhMemDTO mem);
	//회원의 강아지 삭제
	int bhMemsDogDelete(BhMemDTO mem);
}
