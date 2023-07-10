package coo.admin.control;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import coo.admin.db.BhAttendMapper;
import coo.admin.db.BhAttendReserDTO;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;

@Controller
public class AdminHomeController {
	
	@Resource
	BhAttendMapper am;
	
	//관리자 메인 화면 및 오늘 등원 강아지 리스트
	@RequestMapping("/admin")
	String bhTodayList(Model mm, BhAttendReserDTO reser, HttpSession session) {
		System.out.println("bhTodayList() 진입");
		
		session.setAttribute("beforePage", "admin");

		List<BhAttendReserDTO> bigDog = am.dayListBig(reser);
		for (BhAttendReserDTO dto : bigDog) {
			String requeNote = am.getRequeNote(dto);
			dto.setReque(requeNote); //요청사항추가('daybyday' 테이블에는 요청사항이 등록되지 않음)
		}
		List<BhAttendReserDTO> smallDog = am.dayListSmall(reser);
		for (BhAttendReserDTO dto : smallDog) {
			String requeNote = am.getRequeNote(dto);
			dto.setReque(requeNote);
		}
		
		mm.addAttribute("totAttBig", am.bhCountAttBig(reser)); //출석예정 수
		mm.addAttribute("realAttBig", am.bhCountRealBig(reser)); //실제출석 수
		mm.addAttribute("totAttSmall", am.bhCountAttSmall(reser));
		mm.addAttribute("realAttSmall", am.bhCountRealSmall(reser));
		
		mm.addAttribute("bigDog", bigDog);
		//System.out.println("bigDog: "+bigDog);
		mm.addAttribute("smallDog", smallDog);
		//System.out.println("smallDog: "+smallDog);
		mm.addAttribute("reser", reser);
		
		return "admin/adminHome";
	}
	
	//메모출력
	@RequestMapping("/admin/attendMemo/{todayNo}")
	String attendMemo(Model mm, BhAttendReserDTO reser, HttpSession session) {
		System.out.println("attendMemo() 진입");
		reser = am.bhAttMemo(reser);
		mm.addAttribute("bhDogData", reser);
		mm.addAttribute("bhDogImg", am.bhDogImg(reser));
		return "admin/attendToday/bhAttMemo";
	}
	
	//메모수정
	@GetMapping("/admin/attMemoModi/{todayNo}")
	String attendMemoModi(Model mm, BhAttendReserDTO reser, HttpSession session) {
		System.out.println("attendMemoModi() 진입");
		mm.addAttribute("bhDogData", am.bhAttMemo(reser));
		return "admin/attendToday/bhAttMemoModify";
	}

	//메모수정완료
	@PostMapping("/admin/attMemoModi/{todayNo}")
	String attendMemoModiDone(Model mm, BhAttendReserDTO reser, HttpSession session) {
		int chk = am.bhAttMemoModi(reser);
		System.out.println("attendMemoModiDone() 진입");
		String msg = "수정이 되지 않았습니다.";
		String goUrl = "/admin/attMemoModi/"+reser.getTodayNo();
		if(chk==1) {
			msg = "수정되었습니다.";
			goUrl = "/admin/attendMemo/"+reser.getTodayNo();
		}
		mm.addAttribute("msg", msg);
		mm.addAttribute("goUrl", goUrl);
		
		return "admin/attendToday/bhalert";
	}
	
	//등원
	@PostMapping("/admin/attendTime")
	@ResponseBody
	String attendTime(@RequestParam("todayNo") int todayNo, Model mo) {
		System.out.println("attendTime() 진입, todayNo: "+todayNo);
				
		am.checkAttendTime(todayNo);
		
		return "등원완료";
	}
	
	//하원
	@PostMapping("/admin/afterHome")
	@ResponseBody
	String afterHome(@RequestParam("todayNo") int todayNo, Model mo) {
		System.out.println("afterHome() 진입");
		System.out.println("todayNo:"+todayNo);
		am.checkGoHomeTime(todayNo);
		
		BhAttendReserDTO chkTime = am.bhChkTime(todayNo);
		//System.out.println("하원시간:"+chkTime.getGoHome()); //Sun Jul 09 01:51:40 KST 2023
		Date goHome = chkTime.getGoHome();
	
		//마감시간 설정
		Date deadline = chkTime.getGoHome();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(deadline);
		calendar.set(Calendar.HOUR_OF_DAY, 19); // 7시까지 하원
		calendar.set(Calendar.MINUTE, 0); // 분
		calendar.set(Calendar.SECOND, 0); // 초
		/*if(!chkTime.getEdu().equals("-")) { //교육있으면 하원 시간 연장 => 안함
			calendar.set(Calendar.HOUR_OF_DAY, 17);
		}*/
		deadline = calendar.getTime();
		
		System.out.println("goHome:"+goHome+", goHome.getTime():"+goHome.getTime());
		System.out.println("deadline:"+deadline+", deadline.getTime():"+deadline.getTime());
		double timeGap = 0;
		int addPanelty = 0;
		if (goHome.getTime() > deadline.getTime()) {
			System.out.println("추가금 지불");
			timeGap = (double) (goHome.getTime() - deadline.getTime());
			timeGap = timeGap/1000;
			System.out.println("timeGap:"+timeGap);
			System.out.println("시:"+(int)timeGap/(3600));
			System.out.println("분:"+(int)(timeGap%3600)/60);
			System.out.println("초:"+(int)((timeGap%3600)%60));
			
			addPanelty = (int) Math.ceil(timeGap/3600);
			System.out.println(timeGap/3600+","+addPanelty);
			chkTime.setTimeGap(addPanelty);
			
			am.bhAddPanelty(chkTime);
		}

		String msg = "하원완료";
		if(addPanelty>0) {
			msg = "패널티fee: "+(addPanelty*10000);
		}
		return msg;
	}
	
	@RequestMapping("/admin/attList")
	String bhAttendList(Model mm, BhAttendReserDTO reser, HttpSession session) {
		System.out.println("bhAttendList() 진입");
		
		List<BhAttendReserDTO> bigDog = am.dayListBig(reser);
		List<BhAttendReserDTO> smallDog = am.dayListSmall(reser);

		session.setAttribute("beforePage", "attList");
		
		int totAttBig = am.bhCountAttBig(reser);
		mm.addAttribute("totAttBig",totAttBig);
		int realAttBig = am.bhCountRealBig(reser);
		mm.addAttribute("realAttBig",realAttBig);
		int totAttSmall = am.bhCountAttSmall(reser);
		mm.addAttribute("totAttSmall",totAttSmall);
		int realAttSmall = am.bhCountRealSmall(reser);
		mm.addAttribute("realAttSmall",realAttSmall);
		
		
		mm.addAttribute("bigDog", bigDog);
		//System.out.println("bigDog: "+bigDog);
		mm.addAttribute("smallDog", smallDog);
		//System.out.println("smallDog: "+smallDog);
		mm.addAttribute("reser", reser);
		
		return "admin/attendToday/bhAttList";
	}
	

	
}
