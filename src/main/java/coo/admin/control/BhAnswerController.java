package coo.admin.control;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import coo.admin.db.BhQnADTO;
import coo.admin.db.BhQnAMapper;
import coo.admin.model.BhPData;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;

@Controller
public class BhAnswerController {
	
	@Resource
	BhQnAMapper qm;
	
//질문리스트
	@RequestMapping("/admin/answerList/{nowPage}")
	String bhAnsList(Model mo, BhPData pd, BhQnADTO qna, HttpSession session) {
		String pid = (String)session.getAttribute("pid");
		mo.addAttribute("pid", pid);
		
		pd.setTotal(qm.bhQnaTotal(pd));
		List<BhQnADTO> bhAnsData = qm.bhAnsList(pd);
		System.out.println("bhAnsList() 진입");
		
		mo.addAttribute("bhAnsData",bhAnsData);
		mo.addAttribute("pd", pd);
		
		return "admin/answer/bhAnsList";
	}
	
//질문상세
	@RequestMapping("/admin/answer/{no}")
	String bhAnsDetail(Model mo, BhPData pd, BhQnADTO qna, HttpSession session) {
		System.out.println("bhAnsDetail() 진입");
		String pid = (String)session.getAttribute("pid");
		mo.addAttribute("pid", pid);
		
		//pd.setTotal(qm.bhQnaTotal(pd));
		//mo.addAttribute("pd", pd);
		mo.addAttribute("bhAnsData", qm.bhAnsDetail(qna));
		return "admin/answer/bhAnsDetail";
	}

//답변 작성
	@GetMapping("/admin/ansInsert/{no}")
	String bhAnsInsert(Model mo, BhQnADTO qna) {
		System.out.println("bhAnsInsert() 진입");
		mo.addAttribute("bhAnsData", qm.bhAnsDetail(qna));
		System.out.println("아이디"+qna.getAdminId());
		return "admin/answer/bhAnsInsert";
	}

//답변 작성 완료
	@PostMapping("/admin/ansInsert/{no}")
	String bhAnsInsertDone(Model mo, BhQnADTO qna, HttpSession session) {
		String pid = (String)session.getAttribute("pid");
		qna.setAdminId(pid);
		int cnt = qm.bhAnsInsert(qna);
		System.out.println("bhAnsInsertDone() 진입");

		String msg = "작성 되지 않았습니다.";
		String goUrl = "/admin/ansInsert/"+qna.getNo();
		if(cnt==1) {
			msg = "작성 되었습니다.";
			goUrl = "/admin/answer/"+qna.getNo();
		}
		System.out.println("완료:"+cnt);
		mo.addAttribute("msg", msg);
		mo.addAttribute("goUrl", goUrl);
		return "admin/answer/bhalert";
	}
	
	
//답변 수정
	@GetMapping("/admin/ansModi/{no}")
	String bhAnsModi(Model mo, BhQnADTO qna, HttpSession session) {
		System.out.println("bhAnsModi() 진입");
		String pid = (String)session.getAttribute("pid");
		qna.setAdminId(pid);
		mo.addAttribute("pid", pid);
		mo.addAttribute("bhAnsData", qm.bhAnsDetail(qna));
		return "admin/answer/bhAnsModi";
	}

//답변 수정 완료
	@PostMapping("/admin/ansModi/{no}")
	String bhAnsModiDone(Model mo, BhQnADTO qna, HttpSession session) {
		String pid = (String)session.getAttribute("pid");
		qna.setAdminId(pid);
		int cnt = qm.bhAnsModi(qna);
		System.out.println("bhAnsModiDone() 진입");

		String msg = "작성 되지 않았습니다.";
		String goUrl = "/admin/ansModi/"+qna.getNo();
		if(cnt==1) {
			msg = "작성 되었습니다.";
			goUrl = "/admin/answer/"+qna.getNo();
		}
		System.out.println("완료:"+cnt);
		mo.addAttribute("msg", msg);
		mo.addAttribute("goUrl", goUrl);
		return "admin/answer/bhalert";
	}
	
//삭제 전 재확인 페이지
	@GetMapping("/admin/ansDelete/{no}")
	String bhAnsDelete(BhQnADTO qna, Model mo) {
		mo.addAttribute("bhAnsData",qna);
		System.out.println("bhAnsDelete() 진입");
		return "admin/answer/bhAnsDelete";
	}
//삭제완료
	@PostMapping("/admin/ansDelete/{no}")
	String bhAnsDeleteDone(Model mo, BhQnADTO qna) { //, BhDogsDTO dog
		int chk = qm.bhAnsDelete(qna);
		System.out.println("bhAnsDeleteDone() 진입");

		String msg = "삭제 되지 않았습니다.";
		String goUrl = "/admin/ansDelete/"+qna.getNo();
		if(chk==1) {
			msg = "삭제되었습니다.";
			goUrl = "/admin/answer"; //질문리스트
		}
		System.out.println("삭제갯수:"+chk);
		mo.addAttribute("msg", msg);
		mo.addAttribute("goUrl", goUrl);
		return "admin/answer/bhalert";
	}
	
}
