package coo.admin.db;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BhDogMapper {

	List<BhDogsDTO> bhDogList(BhDogsDTO dog);
//애견 주인 이름 list에 저장
	String bhFindMemName(BhDogsDTO dog);
	
	BhDogsDTO bhDogDetail(BhDogsDTO dog);
	
	BhMemDTO bhDogsMem(BhDogsDTO dog);
	
	int bhDogModify(BhDogsDTO dog);
	
	int bhDogDelete(BhDogsDTO dog);
	
	BhMemDTO bhGetMemInform(BhDogsDTO dog);
	
	int bhDogsNameDelete(BhMemDTO mem);	
}
