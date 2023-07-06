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
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class HmLoginController {
	@Resource
	HmLoginMapper lm;
	
	//회원가입///////////////////////////////////////
	@GetMapping("/user/log/joinForm")
	String joinForm(HmMemberDTO dto, HmDogsDTO dog) {
		return "user/log/joinForm";
	}
	
	@PostMapping("/user/log/joinForm")
	String joinComplete(HttpServletRequest request, Model mm, HmMemberDTO dto, HmDogsDTO dog, HmFileData fd) {
		dto.setGrade("일반");
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	    try {
	        Date birthDate = format.parse(fd.getBirthstr());
	        dto.setBirth(birthDate);
	    } catch (ParseException e) {
	        // 날짜 변환 실패 시
	        e.printStackTrace();
	    }
		lm.insert(dto);
		
		String pid = dto.getPid();
		dto.setPid(pid);
		dog.setPhoto(fd.getDogimg().getOriginalFilename());
		
		HmMemberDTO memData = lm.my(pid);
		fileSave(fd.getDogimg(), request);
		lm.insertDog(dog);
		
		if(memData.getDog1()==null || memData.getDog1().equals("")) {
			memData.setDog1(dog.getDname());
			lm.dnameset(memData);
		}
		else if(memData.getDog2()==null || memData.getDog2().equals("")) {
			memData.setDog2(dog.getDname());
			lm.dnameset(memData);
		}
		else if(memData.getDog3()==null || memData.getDog3().equals("")) {
			memData.setDog3(dog.getDname());
			lm.dnameset(memData);
		}
		
		mm.addAttribute("msg","회원가입이 완료되었습니다.");
		mm.addAttribute("goUrl","/user");
		return "user/log/alert";
	}
	
	//애견등록///////////////////////////////////////
	@GetMapping("/user/log/dogJoinForm")
	String dogJoinForm(HmDogsDTO dto) {
		return "user/log/dogJoinForm";
	}
	
	@PostMapping("/user/log/dogJoinForm")
	String dogJoinComplete(HttpServletRequest request, HttpSession session, Model mm, HmDogsDTO dto, HmMemberDTO member, HmFileData fd) {
		String pid = (String)session.getAttribute("pid");
		dto.setPid(pid);
		dto.setPhoto(fd.getDogimg().getOriginalFilename());
		
		HmMemberDTO memData = lm.my(pid);
		fileSave(fd.getDogimg(), request);
		lm.insertDog(dto);
		
		if(memData.getDog1()==null || memData.getDog1().equals("")) {
			memData.setDog1(dto.getDname());
			lm.dnameset(memData);
		}
		else if(memData.getDog2()==null || memData.getDog2().equals("")) {
			memData.setDog2(dto.getDname());
			lm.dnameset(memData);
		}
		else if(memData.getDog3()==null || memData.getDog3().equals("")) {
			memData.setDog3(dto.getDname());
			lm.dnameset(memData);
		}
		
		mm.addAttribute("msg","강아지 등록이 완료되었습니다.");
		mm.addAttribute("goUrl","/user");
		return "user/log/alert";
	}
	
	//로그인///////////////////////////////////////
	@GetMapping("/user/log/login")
	String login(HmMemberDTO dto) {
		return "user/log/login";
	}
	
	@PostMapping("/user/log/login")
	String logincomplete(HmMemberDTO dto, HttpSession session, Model mm) {
		System.out.println("로그인창");
		//int cnt = lm.logchk(dto);
		HmMemberDTO memData = lm.logchk(dto);
		
		if(lm.logchk(dto)!=null && !memData.getGrade().equals("블랙")) {
			mm.addAttribute("msg",memData.getPname()+"님 로그인 되었습니다.");
			mm.addAttribute("goUrl","/user");
			session.setAttribute("pid", memData.getPid());
			session.setAttribute("pname", memData.getPname());
		}
		else {
			mm.addAttribute("msg","없는 회원입니다.");
			mm.addAttribute("goUrl","/user/log/login");
		}
		return "user/log/alert";
	}
	
	//로그아웃///////////////////////////////////////
	@RequestMapping("/user/log/logout")
	String logout(HttpSession session, Model mm) {
		Object pname = session.getAttribute("pname");
		
		session.invalidate();
		
		mm.addAttribute("msg",pname+"님 로그아웃되었습니다.");
		mm.addAttribute("goUrl","/user");
		
		return "user/log/alert";
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
