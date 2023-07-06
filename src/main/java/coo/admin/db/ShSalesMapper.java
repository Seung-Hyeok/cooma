package coo.admin.db;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import coo.admin.model.PData;


@Mapper
public interface ShSalesMapper {
	
	List<ShReservationDTO> salesList(PData qweqwe);

}
