package coo.admin.db;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BhMemMapper {

	List<BhMemDTO> bhMemList(BhMemDTO mem);
	
	BhMemDTO bhMemDetail(BhMemDTO mem);
	
	List<BhDogsDTO> bhMemsDog(BhMemDTO mem);
	
	int bhMemModify(BhMemDTO mem);
	
	int bhMemDelete(BhMemDTO mem);
	
	int bhMemsDogDelete(BhMemDTO mem);
}
