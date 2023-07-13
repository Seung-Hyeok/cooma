package coo.user.control;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import coo.user.model.HmPData;
import coo.user.db.HmQnaDTO;
import coo.user.db.HmQuestionMapper;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;

@Controller
public class HmQuestionController {
	@Resource
	HmQuestionMapper qm;
	
	//질문등록///////////////////////////////////////
	@GetMapping("/user/question/questionInsert")
	String QuestionForm(HttpSession session, Model mm, HmQnaDTO qna) {
		String pid = (String)session.getAttribute("pid");
		mm.addAttribute("pid", pid);
		
		return "user/question/questionInsert";
	}
	
	@PostMapping("/user/question/questionInsert")
	String QuestionComplete(HttpSession session, Model mm, HmQnaDTO qna) {
		String pid = (String)session.getAttribute("pid");
		qna.setPid(pid);
		qm.queInsert(qna);
		int no = qm.maxNo();
		
		mm.addAttribute("msg","문의등록이 완료되었습니다.");
		mm.addAttribute("goUrl","/user/question/queDetail/"+no);
		return "user/question/alert";
	}
	
	//내질문리스트///////////////////////////////////////
	@RequestMapping("/user/question/queList/{nowPage}")
	String queList(HttpSession session, Model mm, HmQnaDTO qna, HmPData hp) {
		System.out.println("질문리스트 진입");
		String pid = (String)session.getAttribute("pid");
		qna.setHp(new HmPData(5,5, hp.getNowPage()));
		qna.setPid(pid);
		qna.hp.setTotal(qm.qtotal(qna));
		mm.addAttribute("pid", pid);
		List<HmQnaDTO> myqnalist = qm.queList(qna);
		mm.addAttribute("myqnalist", myqnalist);
		mm.addAttribute("hp", qna.hp);
		
		System.out.println(hp);
		
		return "user/question/queList";
	}
	
	@RequestMapping("/user/question/a/{ano}/{nowPage}")
	String dbListPname(HttpSession session, Model mm, HmQnaDTO qna, HmPData hp) {
		String pid = (String)session.getAttribute("pid");
		mm.addAttribute("pid", pid);
		qna.setHp(new HmPData(5,5, hp.getNowPage()));
		qna.setPid(pid);
		qna.hp.setTotal(qm.qtotal(qna));
		List<HmQnaDTO> myqnalist = qm.ab(qna);
		//System.out.println("mainData:"+mainData);
		mm.addAttribute("myqnalist", myqnalist);
		mm.addAttribute("hp", qna.hp);
		return "user/question/queList";
	}
	
	//질문디테일///////////////////////////////////////
		@RequestMapping("/user/question/queDetail/{no}")
		String queDetail(HttpSession session, Model mm, HmQnaDTO qna) {
			
			String pid = (String)session.getAttribute("pid");
			mm.addAttribute("pid", pid);
			
			qna.setPid(pid);
			HmQnaDTO myqna = qm.queDetail(qna);
			qm.cntPlus(myqna);
			
			mm.addAttribute("myqna", myqna);
			
			return "user/question/queDetail";
		}
		
		//질문수정///////////////////////////////////////
		@GetMapping("/user/question/queModify/{no}")
		String queModify(HttpSession session, Model mm, HmQnaDTO qna) {
			
			String pid = (String)session.getAttribute("pid");
			mm.addAttribute("pid", pid);
			
			qna.setPid(pid);
			HmQnaDTO myqna = qm.queDetail(qna);
			
			mm.addAttribute("myqna", myqna);
			
			return "user/question/queModify";
		}  
		
		@PostMapping("/user/question/queModify/{no}")
		String queModifyComplete(HttpSession session, Model mm, HmQnaDTO qna) {
			
			String pid = (String)session.getAttribute("pid");
			qna.setPid(pid);
			qm.queModify(qna);
			
			mm.addAttribute("msg", "질문이 수정되었습니다.");
			mm.addAttribute("goUrl", "/user/question/queDetail/"+qna.getNo());
			
			return "user/myPage/alert";
		}
		
		//질문삭제///////////////////////////////////////
		@RequestMapping("/user/question/queDelete/{no}")
		String dogDelete(HttpSession session, Model mm, HmQnaDTO qna) {
			String pid = (String)session.getAttribute("pid");
			qna.setPid(pid);
			qm.queDelete(qna);
			
			mm.addAttribute("msg","질문이 삭제되었습니다.");
			mm.addAttribute("goUrl","/user/question/queList/1");
			
			return "user/question/alert";
		}
}
