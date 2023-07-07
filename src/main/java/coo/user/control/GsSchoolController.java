package coo.user.control;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import coo.user.db.GsDogDTO;
import coo.user.db.GsReserDTO;
import coo.user.db.GsReserMapper;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;

@Controller
public class GsSchoolController {
	
	@Resource
	GsReserMapper grm;
	
	
	@RequestMapping("/user/school")
	String dogSelect(HttpSession session,Model mm) {
		String pid = (String)session.getAttribute("pid");
		List<GsDogDTO> dogData = grm.dogArr(pid);
		System.out.println("dogSelect 진입");
		System.out.println("dogData"+dogData);
		mm.addAttribute("dogData", dogData);
		return "user/school/gsDogSelect";
	}
	@GetMapping("/user/school/packSelect/{dname}")
	String packSelect(HttpSession session,Model mm, GsDogDTO gdto) {
		String pid = (String)session.getAttribute("pid");
		gdto.setPid(pid);
		System.out.println("packSelect 진입");
		System.out.println("drto"+gdto);
		GsDogDTO dogData = grm.pack(gdto);
		mm.addAttribute("packFilter", dogData.getDogsize());
		return "user/school/gsPackSelect";
	}
	@PostMapping("/user/school/packSelect/{dname}")
	String detailSelect(Model mm, GsReserDTO drto) {
		System.out.println("detailSelect 진입");
		System.out.println("drto"+drto);
		return "user/school/gsDetailSelect";
	}
	
	@PostMapping("/user/school/gsBuy")
	String pay(HttpSession session,Model mm, GsReserDTO drto, GsDogDTO gdto) {
		String pid = (String)session.getAttribute("pid");
		drto.setPid(pid);
		System.out.println("gsBuy 진입");
		System.out.println("drto : "+drto.gap());
		
		mm.addAttribute("gapStr",drto.gap());
		
		
		return "user/school/gsBuy";
	}

	@PostMapping("/user/school/gsFin")
	String gsFin(HttpSession session,Model mm, GsReserDTO drto) {
		String pid = (String)session.getAttribute("pid");
		drto.setPid(pid);
		System.out.println("gsFin 진입");
		System.out.println("drto"+drto);
		grm.buy(drto);
		mm.addAttribute("msg","결제완료");
		mm.addAttribute("goUrl", "/user/school");
		return "user/school/alert";
	}
}
