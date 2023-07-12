package coo.user.db;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface GsMyPageMapper {
	
	List<GsReserDTO> reserArr(String dashkjdh);
	
	List<GsReserDTO> oldArr(String dashkjdh);
	
	List<GsReserDTO> show(GsReserDTO daaw);
	List<GsReserDTO> noShow(GsReserDTO daaw);
	List<GsReserDTO> future(GsReserDTO daaw);
	
	
	GsReserDTO buyDetail(GsReserDTO dashkjdh);
	
	int myRefun(GsReserDTO dashkjdh);
	
	int attend(GsReserDTO kk);
}
