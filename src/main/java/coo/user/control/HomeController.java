package coo.user.control;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {
	
	@RequestMapping("/user")
	String home(HttpSession session, Model mm) {
		System.out.println("home 실행");
		
		Object pname = session.getAttribute("pname");
		
		if(pname!=null) {
			mm.addAttribute("msg",pname+"님 반갑습니다.");
		}
		
		return "user/userHome/home";
	}
}
