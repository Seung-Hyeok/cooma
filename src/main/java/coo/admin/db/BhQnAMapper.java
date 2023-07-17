package coo.admin.db;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import coo.admin.model.BhPData;

@Mapper
public interface BhQnAMapper {
	//리스트
	int bhQnaTotal(BhPData pd);
	List<BhQnADTO> bhAnsList(BhPData pd);
	
	//디테일
	BhQnADTO bhAnsDetail(BhQnADTO qna);
	
	//답변 작성
	int bhAnsInsert(BhQnADTO qna);
	
	//수정
	int bhAnsModi(BhQnADTO qna);
	
	//삭제
	int bhAnsDelete(BhQnADTO qna);
}
