package coo.admin.db;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BhAttendMapper {
	List<BhAttendReserDTO> listBig();
	List<BhAttendReserDTO> listSmall();
}
