package coo.admin.db;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import coo.admin.model.PData;


@Mapper
public interface ShReservationMapper {
	
	List<ShReservationDTO> list(PData qweqwe);
	List<ShReservationDTO> refundList(ShReservationDTO davsdv);
	ShReservationDTO detail(ShReservationDTO jklkj);
	int modify(ShReservationDTO refv);
	int delete(ShReservationDTO efev45rtrg);

}
