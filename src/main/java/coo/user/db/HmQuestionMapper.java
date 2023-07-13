package coo.user.db;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface HmQuestionMapper {
	//질문등록
	void queInsert(HmQnaDTO dsfd);
	int maxNo();
	
	//질문리스트
	List<HmQnaDTO> queList(HmQnaDTO dsfdsf);
	
	List<HmQnaDTO> ab(HmQnaDTO dsfdsf);
	
	//질문디테일 
	HmQnaDTO queDetail(HmQnaDTO dsfdsf);
	void cntPlus(HmQnaDTO dsf);
	
	//질문수정
	void queModify(HmQnaDTO dsf);
	
	//질문삭제
	void queDelete(HmQnaDTO dsf);
	
	int qtotal(HmQnaDTO nnng);
}
