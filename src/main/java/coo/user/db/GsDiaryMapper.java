package coo.user.db;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface GsDiaryMapper {
	
	List<GsReserDTO> showP(GsReserDTO gdto);
	List<GsReserDTO> noShowP(GsReserDTO gdto);
	List<GsReserDTO> futureP(GsReserDTO gdto);
	
	List<String> dnames(GsReserDTO gdto);
}
