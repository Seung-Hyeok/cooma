package coo.user.db;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface GsReserMapper {
	void buy(GsReserDTO dasdaa);
	void dayBuy(GsReserDTO dasdaa);
	
	List<GsReserDTO> reserDArr(GsReserDTO drto);
	
	List<GsReserDTO> reserDog(String dashkjdh);
	
	List<GsDogDTO> dogArr(String dashkjdh);
	
	GsDogDTO pack(GsDogDTO dasdaa);
	
	
}
