package coo.user.control;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import coo.user.db.GsDogDTO;
import coo.user.db.GsMyPageMapper;
import coo.user.db.GsReserDTO;
import coo.user.db.GsReserMapper;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;

@Controller
public class GsMyPageController {
	
	@Resource
	GsMyPageMapper gmm;
	
	
	@RequestMapping("/user/myPage/gsMyList")
	String myList(HttpSession session,Model mm) {
		String pid = (String)session.getAttribute("pid");
		List<GsReserDTO> arr = gmm.reserArr(pid);
		mm.addAttribute("buyList", arr);
		System.out.println("myList 진입"+arr);
		return "user/myPage/gsMyList";
	}
	
	@RequestMapping("/user/myPage/gsMyOld")
	String myOld(HttpSession session,Model mm) {
		String pid = (String)session.getAttribute("pid");
		List<GsReserDTO> arr = gmm.oldArr(pid);
		mm.addAttribute("oldList", arr);
		System.out.println("myOld 진입"+arr);
		return "user/myPage/gsMyOld";
	}
	
	@RequestMapping("/user/myPage/buyDetail/{reserNo}")
	String myDetail(HttpSession session,Model mm,GsReserDTO gdto) {
		GsReserDTO bd = gmm.buyDetail(gdto);
		mm.addAttribute("myDetail", bd);
		System.out.println("myDetail 진입"+bd);
		return "user/myPage/gsBuyDetail";
	}


}
