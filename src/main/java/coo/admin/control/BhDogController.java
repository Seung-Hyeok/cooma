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
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class BhDogController {

	@Resource
	BhDogMapper dm;
	
//애견리스트
	@RequestMapping("/dogs")
	String bhDogList(Model mo, BhDogsDTO dog, HttpSession session) {
		List<BhDogsDTO> bhDogData = dm.bhDogList(dog);
		for (BhDogsDTO dto : bhDogData) {
			String name = dm.bhFindMemName(dto);
			dto.setPname(name);
			System.out.println("dto.getPname(): "+dto.getPname());
		}
		session.setAttribute("beforePage", "dogs");
		System.out.println("bhDogList() 진입");
		mo.addAttribute("bhDogData",bhDogData);
		return "admin/dogs/bhDogList";
	}
		
//애견상세
	@RequestMapping("/dogInform/{dname}/{pid}")
	String bhDogDetail(Model mo, BhMemDTO mem, BhDogsDTO dog, HttpSession session) {
		System.out.println("bhDogDetail() 진입");
		mo.addAttribute("bhDogData", dm.bhDogDetail(dog));
		BhMemDTO bhMemData = dm.bhDogsMem(dog);
		mo.addAttribute("bhMemData", bhMemData);
		mo.addAttribute("beforePage", session.getAttribute("beforePage"));
		List<BhAttendReserDTO> bhDogsReser = dm.bhDogsReser(dog);
		mo.addAttribute("bhDogsReser",bhDogsReser);
		
		return "admin/dogs/bhDogDetail";
	}
	
//애견수정
	@GetMapping("/dogModi/{dname}/{pid}")
	String bhDogModifyForm(Model mo, BhMemDTO mem, BhDogsDTO dog) {
		System.out.println("bhDogModifyForm() 진입");

		mo.addAttribute("bhDogData", dm.bhDogDetail(dog));
		BhMemDTO bhMemData = dm.bhDogsMem(dog);
		mo.addAttribute("bhMemData", bhMemData);
		return "admin/dogs/bhDogModifyForm";
	}

//애견수정완료
	@PostMapping("/dogModi/{dname}/{pid}")
	String bhDogModifyDone(Model mo, BhMemDTO mem, BhDogsDTO dog) {
		int cnt = dm.bhDogModify(dog);
		System.out.println("bhDogModifyDone() 진입");

		String msg = "수정이 되지 않았습니다.";
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
	String bhDogDeleteForm(@PathVariable String dname, @PathVariable String pid, Model mo) {
		mo.addAttribute("dname",dname);
		mo.addAttribute("pid",pid);
		System.out.println("bhDogDeleteForm() 진입");
		return "admin/dogs/bhDogDeleteForm";
	}
//애견 삭제완료
	@PostMapping("/dogDelete/{dname}/{pid}")
	String bhDogDeleteDone(Model mo, BhMemDTO mem, BhDogsDTO dog) {
		System.out.println("bhDogDeleteDone() 진입");
		
		int chk1 = dm.bhDogDelete(dog);
		
		BhMemDTO dogsMemData = dm.bhGetMemInform(dog);
		dogsMemData.setDname(mem.getDname());
		int chk2 = dm.bhDogsNameDelete(dogsMemData);
		//System.out.println("dogsMemData.getDname(): "+dogsMemData.getDname());
		//System.out.println("dogsMemData.getDog1(): "+dogsMemData.getDog1());
		
		String msg = "삭제 되지 않았습니다.";
		String goUrl = "/admin/dogDelete/"+dog.getDname()+dog.getPid();
		if((chk1+chk2) >= 2) {
			msg = "삭제되었습니다.";
			goUrl = "/admin/dogs"; //애견리스트
		}
		System.out.println("삭제갯수:"+(chk1+chk2));
		mo.addAttribute("msg", msg);
		mo.addAttribute("goUrl", goUrl);
		return "admin/dogs/bhalert";
	}
}
