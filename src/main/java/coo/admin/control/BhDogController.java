package coo.admin.control;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import coo.admin.db.BhAttendReserDTO;
import coo.admin.db.BhDogMapper;
import coo.admin.db.BhDogsDTO;
import coo.admin.db.BhMemDTO;
import coo.admin.model.BhPData;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class BhDogController {

	@Resource
	BhDogMapper dm;
	
//애견리스트
	@RequestMapping("/dogs/{nowPage}")
	String bhDogList(Model mo, BhDogsDTO dog, BhPData pd, HttpSession session) {
		String pid = (String)session.getAttribute("pid");
		mo.addAttribute("pid", pid);
		
		System.out.println("bhDogList() 진입");
		
		pd.setTotal(dm.bhDogsTotal(pd));
		mo.addAttribute("pd", pd);
		
		List<BhDogsDTO> bhDogData = dm.bhDogList(pd);
		for (BhDogsDTO dto : bhDogData) {
			String name = dm.bhFindMemName(dto);
			dto.setPname(name);
			System.out.println("dto.getPname(): "+dto.getPname());
		}
		mo.addAttribute("bhDogData",bhDogData);
		session.setAttribute("beforePage", "dogs");
		
		return "admin/dogs/bhDogList";
	}
		
//애견상세
	@RequestMapping("/dogInform/{dname}/{pid}")
	String bhDogDetail(Model mo, BhMemDTO mem, BhDogsDTO dog, HttpSession session) {
		String pid = (String)session.getAttribute("pid");
		mo.addAttribute("pid", pid);
		
		System.out.println("bhDogDetail() 진입");
		mo.addAttribute("bhDogData", dm.bhDogDetail(dog));
		BhMemDTO bhMemData = dm.bhDogsMem(dog);
		mo.addAttribute("bhMemData", bhMemData); //회원 이름 정보 가져오기
		mo.addAttribute("beforePage", session.getAttribute("beforePage"));
		//애견 예약 정보
		List<BhAttendReserDTO> bhDogsReser = dm.bhDogsReser(dog);
		mo.addAttribute("bhDogsReser",bhDogsReser);
		
		return "admin/dogs/bhDogDetail";
	}
	
//애견수정
	@GetMapping("/dogModi/{dname}/{pid}")
	String bhDogModifyForm(Model mo, BhMemDTO mem, BhDogsDTO dog, HttpSession session) {
		String pid = (String)session.getAttribute("pid");
		mo.addAttribute("pid", pid);
		
		System.out.println("bhDogModifyForm() 진입");

		mo.addAttribute("bhDogData", dm.bhDogDetail(dog));
		BhMemDTO bhMemData = dm.bhDogsMem(dog);
		mo.addAttribute("bhMemData", bhMemData);
		return "admin/dogs/bhDogModifyForm";
	}

//애견수정완료
	@PostMapping("/dogModi/{dname}/{pid}")
	String bhDogModifyDone(Model mo, BhMemDTO mem, BhDogsDTO dog, HttpSession session) {
		String pid = (String)session.getAttribute("pid");
		mo.addAttribute("pid", pid);
		
		System.out.println("bhDogModifyDone() 진입");
		
		//예약이 있는지 확인 => 예약이 있으면 reserChk = 1
		System.out.println("독등급:"+dog.getGrade());
		int reserChk = 0;
		if(dog.getGrade().equals("블랙")) {
			reserChk = dm.bhDogGradeChk(dog);
		}
		//강아지 등급이 '일반'일때 블랙메모에 입력값이 있는지 확인
		else {
			
		}
		
		//예약이 없으면 변경 
		int cnt = 0;
		if(reserChk == 0) {
			cnt = dm.bhDogModify(dog);
		}
				
		String msg = "예약이 있어 블랙으로 변경되지 않았습니다.";
		String goUrl = "/admin/dogModi/"+dog.getDname()+"/"+dog.getPid();
		
		if(cnt==1) {
			msg = "수정되었습니다.";
			goUrl = "/admin/dogInform/"+dog.getDname()+"/"+dog.getPid();
		}
		System.out.println("수정갯수:"+cnt);
		
		mo.addAttribute("msg", msg);
		mo.addAttribute("goUrl", goUrl);
		return "admin/dogs/bhalert";
	}
	
//애견 삭제 전 재확인 페이지
	@GetMapping("/dogDelete/{dname}/{pid}")
	String bhDogDeleteForm(BhDogsDTO dog, Model mo, HttpSession session) {
		String pid = (String)session.getAttribute("pid");
		mo.addAttribute("pid", pid);
		
		mo.addAttribute("dogData",dog);
		System.out.println("bhDogDeleteForm() 진입");
		return "admin/dogs/bhDogDeleteForm";
	}
	
//애견 삭제완료
	@PostMapping("/dogDelete/{dname}/{pid}")
	String bhDogDeleteDone(Model mo, BhMemDTO mem, BhDogsDTO dog, HttpSession session) {
		String pid = (String)session.getAttribute("pid");
		mo.addAttribute("pid", pid);
		
		System.out.println("bhDogDeleteDone() 진입");
		
		int chkReser = dm.bhDogBeforeDelete(dog);
		
		int chk1=0, chk2=0;
		String msg = "예약이 남아 있어 삭제되지 않습니다.";
		String goUrl = "/admin/dogDelete/"+dog.getDname()+"/"+dog.getPid();
		
		if(chkReser == 0) {
			chk1 = dm.bhDogDelete(dog);
			BhMemDTO dogsMemData = dm.bhGetMemInform(dog);
			dogsMemData.setDname(mem.getDname());
			chk2 = dm.bhDogsNameDelete(dogsMemData);
		}
		
		if((chk1+chk2) >= 2) {
			msg = "삭제되었습니다.";
			goUrl = "/admin/dogs/1"; //애견리스트
		}
		System.out.println("삭제갯수:"+(chk1+chk2));
		mo.addAttribute("msg", msg);
		mo.addAttribute("goUrl", goUrl);
		return "admin/dogs/bhalert";
	}
}
