package coo.user.db;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface HmLoginMapper {
	//회원등록
	void insert(HmMemberDTO dsfdsf);
	
	//회원로그인
	HmMemberDTO logchk(HmMemberDTO sffsf);
	
	//마이페이지
	HmMemberDTO my(String sdf);
	
	//회원탈퇴
	int delete(HmMemberDTO sdf);
	
	//회원수정
	int modify(HmMemberDTO sdf);
	
	//강아지등록 시 회원테이블에 강아지이름 추가
	void dnameset(HmMemberDTO sdf);
	
	//비번수정
	HmMemberDTO pwchk(HmMemberDTO sffsf);
	int pwChange(HmMemberDTO sdf);
	
	//강아지등록
	void insertDog(HmDogsDTO dsf);
	
	//강아지리스트
	List<HmDogsDTO> dogList(HmDogsDTO dsf);
	
	//강아지 상세
	HmDogsDTO dogDetail(HmDogsDTO dto);
	
	//강아지수정
	void dogModify(HmDogsDTO dsf);
	
	//강아지삭제
	void dogDelete(HmDogsDTO dsf);
	
	//아이디중복확인
	int checkId(String sfds);
	
	List<HmDaybydayDTO> checkDay(String sfds);
}
