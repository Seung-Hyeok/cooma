package coo.user.control;

import java.io.FileOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
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
	String joinForm(HttpSession session, Model mm,HmMemberDTO dto, HmDogsDTO dog) {
		String pid = (String)session.getAttribute("pid");
		mm.addAttribute("pid", pid);
		return "user/log/joinForm";
	}
	
	@PostMapping("/user/log/joinForm")
	String joinComplete(HttpServletRequest request, Model mm, HmDogsDTO dog, HmMemberDTO dto, HmFileData fd) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		dto.setEmail(dto.getEmail()+"@"+fd.getEmail2());
	    try {
	        Date birthDate = format.parse(fd.getBirthstr());
	        dto.setBirth(birthDate);
	    } catch (ParseException e) {
	        // 날짜 변환 실패 시
	        e.printStackTrace();
	    }
	    dog.setPhoto(fd.getDogimg().getOriginalFilename());
		fileSave(fd.getDogimg(), request);
		lm.insertDog(dog);
	    dto.setDog1(dog.getDname());
	    lm.insert(dto);
		
		mm.addAttribute("msg","회원가입이 완료되었습니다.");
		mm.addAttribute("goUrl","/user");
		return "user/log/alert";
	}
	
	//로그인///////////////////////////////////////
	@GetMapping("/user/log/login")
	String login(HttpSession session, Model mm,HmMemberDTO dto) {
		String pid = (String)session.getAttribute("pid");
		mm.addAttribute("pid", pid);
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
			if(memData.getGrade().equals("관리자")) {
				mm.addAttribute("goUrl","/admin");
			}
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
		System.out.println("fileSave진입");
		String path = request.getServletContext().getRealPath("dimg");
		System.out.println("path"+path);
		try {
			FileOutputStream fos = new FileOutputStream(path+"/"+ff.getOriginalFilename());
			fos.write(ff.getBytes());
			fos.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/user/checkId.do")
	@ResponseBody
    public String checkId(@RequestParam("pid") String pid) {
        String result="N";
        
        if(pid.isEmpty()) {
            return "F"; // "F"를 반환하여 사용 불가능한 아이디임을 알립니다.
        }
        else {
            int flag = lm.checkId(pid);
            if(flag == 1) {
            	result="Y"; // "Y"를 반환하여 사용 가능한 아이디임을 알립니다.
            }
            else {
            	result="N"; // "N"을 반환하여 사용 불가능한 아이디임을 알립니다.
            }
        }
        //아이디가 있을시 Y 없을시 N 으로jsp view 로 보냄
        return result;
    }
}
