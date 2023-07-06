package coo.admin.control;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import coo.admin.db.BhAttendMapper;
import coo.admin.db.BhAttendReserDTO;
import jakarta.annotation.Resource;

@Controller
public class AdminHomeController {
	
	@Resource
	BhAttendMapper am;
	/*
	@RequestMapping("/admin")
	String adminHome() {
		System.out.println("adminHome() 진입");
		return "admin/adminHome";
	}
	*/
	
	//관리자 메인 화면 및 오늘 등원 강아지 리스트
	@RequestMapping("/admin")
	String bhAttendList(Model mm) {
		List<BhAttendReserDTO> bigDog = am.listBig();
		List<BhAttendReserDTO> smallDog = am.listSmall();
		System.out.println("bhAttendList() 진입");
		mm.addAttribute("bigDog", bigDog);
		System.out.println("bigDog: "+bigDog);
		mm.addAttribute("smallDog", smallDog);
		System.out.println("smallDog: "+smallDog);
		return "admin/adminHome";
	}
	
	
	
}
