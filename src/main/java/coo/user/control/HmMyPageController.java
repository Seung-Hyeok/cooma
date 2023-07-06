package coo.user.control;

import java.io.FileOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import coo.user.db.HmFileData;
import coo.user.db.HmDogsDTO;
import coo.user.db.HmLoginMapper;
import coo.user.db.HmMemberDTO;
import coo.user.db.HmMyPageMapper;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class HmMyPageController {
	@Resource
	HmMyPageMapper mp;
	
	//마이페이지///////////////////////////////////////
		@RequestMapping("/user/myPage/memPage")
		String myPage(HttpSession session, Model mm) {
			
			String pid = (String)session.getAttribute("pid");
			mm.addAttribute("myData", mp.my(pid));
			
			return "user/myPage/memPage";
		}
	
	//회원정보수정///////////////////////////////////////
	@GetMapping("/user/myPage/modifyForm")
	String modifyForm(HttpSession session, Model mm, HmMemberDTO dto) {
		
		String pid = (String)session.getAttribute("pid");
		mm.addAttribute("myData", mp.my(pid));
		
		return "user/myPage/modifyForm";
	}  
	
	@PostMapping("/user/myPage/modifyForm")
	String modifyComplete(HttpSession session, Model mm, HmMemberDTO dto) {
		
		String pid = (String)session.getAttribute("pid");
		dto.setPid(pid);
		int cnt = mp.modify(dto);
		
		String msg = "비밀번호가 일치하지 않습니다.";
		String goUrl = "/user/myPage/modifyForm";
		
		if(cnt>0) {
			msg = "회원정보가 수정되었습니다.";
			goUrl = "/user/myPage/myPage";
			
			HmMemberDTO memData = mp.logchk(dto);
			session.setAttribute("pname", memData.getPname());
		}
		
		mm.addAttribute("msg", msg);
		mm.addAttribute("goUrl", goUrl);
		
		return "user/myPage/alert";
	}
	
	//회원탈퇴///////////////////////////////////////
	@GetMapping("/user/myPage/delete")
	String delete(HttpSession session, Model mm, HmMemberDTO dto) {
		
		return "user/myPage/delete";
	}
	
	@PostMapping("/user/myPage/delete")
	String deleteComplete(HttpSession session, Model mm, HmMemberDTO dto, HmDogsDTO dog) {
		
		String pid = (String)session.getAttribute("pid");
		dto.setPid(pid);
		dog.setPid(pid);
		HmMemberDTO memData = mp.my(pid);
		
		int cnt = mp.delete(dto);
		
		String msg = "비밀번호가 일치하지 않습니다.";
		String goUrl = "/user/myPage/delete";
		
		if(cnt>0) {
			msg = "회원탈퇴가 완료되었습니다.";
			goUrl = "/user";
			dog.setDname(memData.getDog1());
			mp.dogDelete(dog);
			dog.setDname(memData.getDog2());
			mp.dogDelete(dog);
			dog.setDname(memData.getDog3());
			mp.dogDelete(dog);
			session.invalidate();
		}
		
		mm.addAttribute("msg", msg);
		mm.addAttribute("goUrl", goUrl);
		
		return "user/myPage/alert";
	}
	
	//비번수정///////////////////////////////////////
	@GetMapping("/user/myPage/pwChange")
	String pwChange(HttpSession session, Model mm, HmMemberDTO dto) {
		
		return "user/myPage/pwChange";
	}
	
	@PostMapping("/user/myPage/pwChange")
	String pwChangeComplete(HttpSession session, Model mm, HmMemberDTO dto) {
		
		String pid = (String)session.getAttribute("pid");
		dto.setPid(pid);
		//HmMemberDTO memData = lm.pwchk(dto);
		int cnt = mp.pwChange(dto);
		
		String msg = "비밀번호가 일치하지 않습니다.";
		String goUrl = "/user/myPage/pwChange";
		
		if(cnt>0) {
			msg = "비밀번호가 변경되었습니다.";
			goUrl = "/user/myPage/memPage";
		}
		
		mm.addAttribute("msg", msg);
		mm.addAttribute("goUrl", goUrl);
		
		return "user/myPage/alert";
	}
	
	//애견등록///////////////////////////////////////
		@GetMapping("/user/myPage/dogJoinForm")
		String dogJoinForm(HmDogsDTO dto) {
			return "user/myPage/dogJoinForm";
		}
		
		@PostMapping("/user/myPage/dogJoinForm")
		String dogJoinComplete(HttpServletRequest request, HttpSession session, Model mm, HmDogsDTO dto, HmMemberDTO member, HmFileData fd) {
			String pid = (String)session.getAttribute("pid");
			dto.setPid(pid);
			dto.setPhoto(fd.getDogimg().getOriginalFilename());
			
			HmMemberDTO memData = mp.my(pid);
			fileSave(fd.getDogimg(), request);
			mp.insertDog(dto);
			
			if(memData.getDog1()==null || memData.getDog1().equals("")) {
				memData.setDog1(dto.getDname());
				mp.dnameset(memData);
			}
			else if(memData.getDog2()==null || memData.getDog2().equals("")) {
				memData.setDog2(dto.getDname());
				mp.dnameset(memData);
			}
			else if(memData.getDog3()==null || memData.getDog3().equals("")) {
				memData.setDog3(dto.getDname());
				mp.dnameset(memData);
			}
			
			mm.addAttribute("msg","강아지 등록이 완료되었습니다.");
			mm.addAttribute("goUrl","/user");
			return "user/myPage/alert";
		}
	
	//애견리스트///////////////////////////////////////
	@RequestMapping("/user/myPage/dogList")
	String dogList(HttpSession session, Model mm, HmDogsDTO dto) {
		String pid = (String)session.getAttribute("pid");
		dto.setPid(pid);
		List<HmDogsDTO> dogData = mp.dogList(dto);
		//System.out.println("mainData:"+mainData);
		System.out.println(dogData);
		mm.addAttribute("dogData", dogData);
		return "user/myPage/dogList";
	}
	
	//애견디테일///////////////////////////////////////
	@RequestMapping("/user/myPage/dogDetail/{dname}")
	String dogDetail(HttpSession session, Model mm, HmDogsDTO dto) {
		String pid = (String)session.getAttribute("pid");
		dto.setPid(pid);
		mm.addAttribute("dogData",mp.dogDetail(dto));
		session.setAttribute("photo", mp.dogDetail(dto).getPhoto());
		return "user/myPage/dogDetail";
	}
	
	//애견수정///////////////////////////////////////
	@GetMapping("/user/myPage/dogModify/{dname}")
	String dogModify(HttpSession session, Model mm, HmDogsDTO dto) {
		String pid = (String)session.getAttribute("pid");
		dto.setPid(pid);
		System.out.println("dto.getPhoto()1"+dto.getPhoto());
		mm.addAttribute("dogData", mp.dogDetail(dto));
		return "user/myPage/dogModify";
	}
	
	@PostMapping("/user/myPage/dogModify/{dname1}")
	String dogModifyComplete(@PathVariable String dname1, HttpSession session, Model mm, HmDogsDTO dto) {
		String pid = (String)session.getAttribute("pid");
		System.out.println("dto.getPhoto()2"+dto.getPhoto());
		dto.setPid(pid);
		String photo = (String)session.getAttribute("photo");
		if(dto.getPhoto()==null || dto.getPhoto().equals("")) {
			dto.setPhoto(photo);
		}
		mp.dogModify(dto);
		HmMemberDTO memData = mp.my(pid);
		
		if(memData.getDog1().equals(dname1)) {
			memData.setDog1(dto.getDname());
			mp.dnameset(memData);
		}
		else if(memData.getDog2().equals(dname1)) {
			memData.setDog2(dto.getDname());
			mp.dnameset(memData);
		}
		else if(memData.getDog3().equals(dname1)) {
			memData.setDog3(dto.getDname());
			mp.dnameset(memData);
		}
		
		mm.addAttribute("msg", "애견정보 수정이 완료되었습니다.");
		mm.addAttribute("goUrl", "/user/myPage/dogDetail/"+dto.getDname());
		
		return "user/myPage/alert";
	}
	//애견삭제///////////////////////////////////////
	@RequestMapping("/user/myPage/dogDelete/{dname}")
	String dogDelete(HttpSession session, Model mm, HmDogsDTO dto) {
		System.out.println("ddddd");
		String pid = (String)session.getAttribute("pid");
		dto.setPid(pid);
		mp.dogDelete(dto);
		
		HmMemberDTO memData = mp.my(pid);
		
		if(memData.getDog1().equals(dto.getDname())) {
			memData.setDog1(null);
			mp.dnameset(memData);
		}
		else if(memData.getDog2().equals(dto.getDname())) {
			memData.setDog2(null);
			mp.dnameset(memData);
		}
		else if(memData.getDog3().equals(dto.getDname())) {
			memData.setDog3(null);
			mp.dnameset(memData);
		}
		
		mm.addAttribute("msg","애견등록이 해제되었습니다.");
		mm.addAttribute("goUrl","/user/myPage/dogList");
		
		return "user/myPage/alert";
	}
	
	//사진저장///////////////////////////////////////
	void fileSave(MultipartFile ff,	HttpServletRequest request) {
		String path = request.getServletContext().getRealPath("dimg");
		//System.out.println(path);
		try {
			FileOutputStream fos = new FileOutputStream(path+"\\"+ff.getOriginalFilename());
			fos.write(ff.getBytes());
			fos.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
}
