package coo.admin.db;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import coo.admin.model.PData;

@Mapper
public interface ShNoticeMapper {
	
	List<ShNoticeDTO> list(PData vvvczxz);
	ShNoticeDTO detail(ShNoticeDTO uniun);
	int modify(ShNoticeDTO czxvz);
	int delete(ShNoticeDTO efev45rtrg);
	void insert(ShNoticeDTO efev45rtrg);
	void up(ShNoticeDTO sdsds);
	int ntotal(PData nnng);
	
}