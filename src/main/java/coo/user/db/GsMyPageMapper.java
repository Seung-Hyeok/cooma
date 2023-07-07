package coo.user.db;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface GsMyPageMapper {
	
	List<GsReserDTO> reserArr(String dashkjdh);
	
	List<GsReserDTO> oldArr(String dashkjdh);
	
	GsReserDTO buyDetail(GsReserDTO dashkjdh);
	
}
