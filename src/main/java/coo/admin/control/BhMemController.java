package coo.admin.control;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import coo.admin.db.BhDogsDTO;
import coo.admin.db.BhMemDTO;
import coo.admin.db.BhMemMapper;
import coo.admin.model.BhPData;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;


@Controller
@RequestMapping("/admin")
public class BhMemController {
	
	@Resource
	BhMemMapper mm;
	
//회원리스트
	@RequestMapping("/member/{nowPage}")
	String bhMemList(Model mo, BhMemDTO mem, BhPData pd, HttpSession session) {
		String pid = (String)session.getAttribute("pid");
		mo.addAttribute("pid", pid);
		
		pd.setTotal(mm.bhMemTotal(pd));
		mo.addAttribute("pd",pd);
		
		List<BhMemDTO> bhMemData = mm.bhMemList(pd);
		System.out.println("bhMemList() 진입");
		mo.addAttribute("bhMemData",bhMemData);
		return "admin/mems/bhMemList";
	}
	
//회원상세
	@RequestMapping("/memPid/{pid}") // /{nowPage}
	String bhMemDetail(Model mo, BhMemDTO mem, BhDogsDTO dog, HttpSession session) {
		String pid = (String)session.getAttribute("pid");
		mo.addAttribute("pid", pid);
		
		System.out.println("bhMemDetail() 진입");
		session.setAttribute("beforePage", "mem");
		mo.addAttribute("bhMemData", mm.bhMemDetail(mem));
		List<BhDogsDTO> bhDogData = mm.bhMemsDog(mem);
		mo.addAttribute("bhDogData", bhDogData);
		//System.out.println("bhDogData:"+bhDogData); //강아지 확인
		return "admin/mems/bhMemDetail";
	}
	
//회원수정
	@GetMapping("/memModi/{pid}")
	String bhMemModifyForm(Model mo, BhMemDTO mem, BhDogsDTO dog, HttpSession session) {
		String pid = (String)session.getAttribute("pid");
		mo.addAttribute("pid", pid);
		
		System.out.println("bhMemModifyForm() 진입");
		
		mo.addAttribute("bhMemData", mm.bhMemDetail(mem));
		List<BhDogsDTO> bhDogData = mm.bhMemsDog(mem);
		mo.addAttribute("bhDogData", bhDogData);
		return "admin/mems/bhMemModifyForm";
	}

//회원수정완료
	@PostMapping("/memModi/{pid}")
	String bhMemModifyDone(Model mo, BhMemDTO mem, HttpSession session) {
		String pid = (String)session.getAttribute("pid");
		mo.addAttribute("pid", pid);
		
		int cnt = mm.bhMemModify(mem);
		System.out.println("bhMemModifyDone() 진입");

		String msg = "수정이 되지 않았습니다.";
		String goUrl = "/admin/memModi/"+mem.getPid();
		if(cnt==1) {
			msg = "수정되었습니다.";
			goUrl = "/admin/memPid/"+mem.getPid();
		}
		System.out.println("수정갯수:"+cnt);
		mo.addAttribute("msg", msg);
		mo.addAttribute("goUrl", goUrl);
		return "admin/mems/bhalert";
	}
	
//삭제 전 재확인 페이지
	@GetMapping("/memDelete/{pid}")
	String bhMemDeleteForm(BhMemDTO mem, Model mo, HttpSession session) {
		String pid = (String)session.getAttribute("pid");
		mo.addAttribute("pid", pid);
		
		mo.addAttribute("memData", mem);
		
		System.out.println("bhMemDeleteForm() 진입");
		return "admin/mems/bhMemDeleteForm";
	}
//삭제완료
	@PostMapping("/memDelete/{pid}")
	String bhMemDeleteDone(Model mo, BhMemDTO mem, BhDogsDTO dog, HttpSession session) {
		String pid = (String)session.getAttribute("pid");
		mo.addAttribute("pid", pid);
		
		System.out.println("bhMemDeleteDone() 진입");
		int chkReser = mm.bhMemBeforeDelete(mem);
		
		int chk1=0, chk2=0;
		String msg = "예약이 남아 있어 삭제되지 않습니다.";
		String goUrl = "/admin/memDelete/"+mem.getPid();
		
		if(chkReser == 0) {
			chk1 = mm.bhMemDelete(mem);
			chk2 = mm.bhMemsDogDelete(mem);
		}
		
		if((chk1+chk2) >= 2) {
			msg = "삭제되었습니다.";
			goUrl = "/admin/member/1"; //회원리스트
		}
		System.out.println("삭제갯수:"+(chk1+chk2));
		mo.addAttribute("msg", msg);
		mo.addAttribute("goUrl", goUrl);
		return "admin/mems/bhalert";
	}	
	
}
