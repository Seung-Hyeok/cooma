package coo.admin.db;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BhQnAMapper {
	//리스트
	List<BhQnADTO> bhAnsList(BhQnADTO qna);
	
	//디테일
	BhQnADTO bhAnsDetail(BhQnADTO qna);
	
	//답변 작성
	int bhAnsInsert(BhQnADTO qna);
	
	//수정
	int bhAnsModi(BhQnADTO qna);
	
	//삭제
	int bhAnsDelete(BhQnADTO qna);
}
