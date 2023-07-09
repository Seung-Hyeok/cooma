package coo.admin.control;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import coo.admin.db.BhAttendMapper;
import coo.admin.db.BhAttendReserDTO;
import coo.admin.db.BhDogsDTO;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class AdminHomeController {
	
	@Resource
	BhAttendMapper am;
	
	//관리자 메인 화면 및 오늘 등원 강아지 리스트
	@RequestMapping("/admin")
	String bhTodayList(Model mm, BhAttendReserDTO reser, HttpSession session) {
		System.out.println("bhTodayList() 진입");
		
		List<BhAttendReserDTO> bigDog = am.todayListBig(reser);
		for (BhAttendReserDTO dto : bigDog) {
			String requeNote = am.getRequeNote(dto);
			dto.setReque(requeNote);
		}
		List<BhAttendReserDTO> smallDog = am.todayListSmall(reser);
		for (BhAttendReserDTO dto : smallDog) {
			String requeNote = am.getRequeNote(dto);
			dto.setReque(requeNote);
		}
		session.setAttribute("beforePage", "admin");
		
		mm.addAttribute("bigDog", bigDog);
		//System.out.println("bigDog: "+bigDog);
		mm.addAttribute("smallDog", smallDog);
		//System.out.println("smallDog: "+smallDog);
		mm.addAttribute("reser", reser);
		
		return "admin/adminHome";
	}
	
	//등원
	@PostMapping("/admin/attendTime")
	String attendTime(@RequestParam("todayNo") int todayNo, Model mo) {
		System.out.println("attendTime() 진입");
		System.out.println("todayNo:"+todayNo);
		am.checkAttendTime(todayNo);
		
		String goUrl = "/admin/adminHome";
		mo.addAttribute("goUrl", goUrl);
		return "admin/attendToday/bhalert";
	}
	
	//하원
	@PostMapping("/admin/afterHome")
	String afterHome(@RequestParam("todayNo") int todayNo, Model mo) {
		System.out.println("afterHome() 진입");
		System.out.println("todayNo:"+todayNo);
		am.checkGoHomeTime(todayNo);
		
		BhAttendReserDTO chkTime = am.bhChkTime(todayNo);
		System.out.println("하원시간:"+chkTime.getGoHome()); //Sun Jul 09 01:51:40 KST 2023
		Date goHome = chkTime.getGoHome();
	//마감시간 설정
		Date deadline = chkTime.getGoHome();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(deadline);
		calendar.set(Calendar.HOUR_OF_DAY, 11); // 시간
		calendar.set(Calendar.MINUTE, 0); // 분
		calendar.set(Calendar.SECOND, 0); // 초
		if(!chkTime.getEdu().equals("-")) { //교육있으면 하원 시간 연장
			calendar.set(Calendar.HOUR_OF_DAY, 12);
		}
		deadline = calendar.getTime();
		
		System.out.println("goHome:"+goHome+", goHome.getTime():"+goHome.getTime());
		System.out.println("deadline:"+deadline+", deadline.getTime():"+deadline.getTime());
		if (goHome.getTime() > deadline.getTime()) {
			System.out.println("추가금 지불");
			double timeGap = (double) (goHome.getTime() - deadline.getTime());
			timeGap = timeGap/1000;
			System.out.println("timeGap:"+timeGap);
			System.out.println("시:"+(int)timeGap/(3600));
			System.out.println("분:"+(int)(timeGap%3600)/60);
			System.out.println("초:"+(int)((timeGap%3600)%60));
			
			int addPanelty = (int) Math.ceil(timeGap/3600);
			System.out.println(timeGap/3600+","+addPanelty);
			chkTime.setTimeGap(addPanelty);
			
			am.bhAddPanelty(chkTime);
			
		}
		
		/*
		String lastTime = "19:00:00";
		if(!chkTime.getEdu().equals("-")) {
			lastTime = "20:00:00";
		}
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        try {
			Date strToDate = sdf.parse(lastTime);
			
			Calendar goHomeCalendar = Calendar.getInstance();
		    goHomeCalendar.setTime(goHome);
		    goHomeCalendar.set(Calendar.YEAR, 1970);
		    goHomeCalendar.set(Calendar.MONTH, 0);
		    goHomeCalendar.set(Calendar.DAY_OF_MONTH, 1);
		    Date goHomeTimeOnly = goHomeCalendar.getTime();
			
		    System.out.println("strToDate:"+strToDate);
			System.out.println("goHomeTimeOnly:"+goHomeTimeOnly);
		    
			System.out.println(goHomeTimeOnly+","+goHomeTimeOnly.getTime()+","+goHome.getTime()+","+strToDate.getTime());

			if (goHomeTimeOnly.getTime() > strToDate.getTime()){
				long timeGap = goHome.getTime() - strToDate.getTime();
				System.out.println(timeGap/1000);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		
		String goUrl = "/admin/adminHome";
		mo.addAttribute("goUrl", goUrl);
		return "admin/attendToday/bhalert";
	}
	
	
	
	
	String bhAttendList() {
		return "";
	}
	
	
}
