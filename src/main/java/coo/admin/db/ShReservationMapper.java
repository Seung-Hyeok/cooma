package coo.admin.db;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import coo.admin.model.PData;


@Mapper
public interface ShReservationMapper {
	
	List<ShReservationDTO> list(PData qweqwe);
	List<ShReservationDTO> endlist(PData asdasdx);
	List<ShReservationDTO> refundList(PData davsdv);
	ShReservationDTO detail(ShReservationDTO jklkj);
	int total(PData jvbjskb);
	int endtotal(PData vcxv);
	int refundtotal(PData vvcxsftd);
	int modify(ShReservationDTO refv);
	int delete(ShReservationDTO efev45rtrg);
	int attendAll(ShReservationDTO kk);
	int clear(ShReservationDTO vxcvsd);
	
	int dayRefun(ShReservationDTO wh);
	
}
