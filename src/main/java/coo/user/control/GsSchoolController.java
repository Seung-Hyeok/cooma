package coo.user.control;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
		mm.addAttribute("pid", pid); 
		if(session.getAttribute("pid") ==null) {
			mm.addAttribute("msg","로그인 후 이용 가능합니다.");
			mm.addAttribute("goUrl","/user/log/login");
			return "user/log/alert";
		}
		List<GsDogDTO> dogData = grm.dogArr(pid);
		List<GsReserDTO> dogReser = grm.reserDog(pid);
		System.out.println("dogSelect 진입");
		System.out.println("dogData"+dogData);
		mm.addAttribute("dogData", dogData);
		mm.addAttribute("dogReser", dogReser);
		return "user/school/gsDogSelect";
	}
	@GetMapping("/user/school/packSelect/{dname}")
	String packSelect(HttpSession session,Model mm, GsDogDTO gdto) {
		String pid = (String)session.getAttribute("pid");
		mm.addAttribute("pid", pid);
		gdto.setPid(pid);
		System.out.println("packSelect 진입");
		System.out.println("drto"+gdto);
		GsDogDTO dogData = grm.pack(gdto);
		mm.addAttribute("packFilter", dogData.getDogsize());
		return "user/school/gsPackSelect";
	}
	@PostMapping("/user/school/packSelect/{dname}")
	String detailSelect(HttpSession session,Model mm, GsReserDTO drto) {
		System.out.println("detailSelect 진입");
		System.out.println("drto"+drto);
		String pid = (String)session.getAttribute("pid");
		mm.addAttribute("pid", pid);
		drto.setPid(pid);
		List<GsReserDTO> reserDArr = grm.reserDArr(drto);
		String[][] reserDArrBuf = new String[reserDArr.size()][2];
		for(int i = 0 ; i<reserDArr.size(); i++) {
			reserDArrBuf[i][0] = reserDArr.get(i).getStartD();
			reserDArrBuf[i][1] = reserDArr.get(i).getEndD();
			System.out.println(reserDArrBuf[i][0]+"====="+reserDArrBuf[i][1]);
		}
		List<GsReserDTO> dogReser = grm.reserDog(pid);
		mm.addAttribute("dogReser", dogReser);
		mm.addAttribute("dayArr", reserDArrBuf);
		mm.addAttribute("gaps", drto.getGap());
		return "user/school/gsDetailSelect";
	}
	
	@PostMapping("/user/school/gsBuy")
	String pay(HttpSession session,Model mm, GsReserDTO drto, GsDogDTO gdto) {
		String pid = (String)session.getAttribute("pid");
		mm.addAttribute("pid", pid);
		
		int cnt = drto.getGap()/7*drto.getWeeks().length();
		if(drto.getEdu().equals("-")) {
			drto.setEduFee(0);
			drto.setTotFee(drto.getPay());
		}
		if(drto.getEdu().equals("배변")) {
			drto.setEduFee(2900*cnt);
			drto.setTotFee(drto.getEduFee()+drto.getPay());
		}
		if(drto.getEdu().equals("분리불안")) {
			drto.setEduFee(4900*cnt);
			drto.setTotFee(drto.getEduFee()+drto.getPay());
		}
		if(drto.getEdu().equals("공격성")) {
			drto.setEduFee(5900*cnt);
			drto.setTotFee(drto.getEduFee()+drto.getPay());
		}
		drto.setPid(pid);
		Date start = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            start = sdf.parse(drto.getStartD());
        } catch (Exception e) {}
        // Calendar 객체 생성 및 기준 날짜 설정
        
        if(drto.getWeeks().equals("0")) {
        	System.out.println("일일권 진입~~~~~~~~");
        	int kk = start.getDay();
        	System.out.println("kk"+kk);
        	String weeks = "일월화수목금토";
        	System.out.println("weeks.split(\"\")[kk]:"+weeks.split("")[kk]);
        	drto.setWeeks(weeks.split("")[kk]);
		}
		System.out.println("gsBuy 진입");
		System.out.println("drto : "+drto.gap());
		
		mm.addAttribute("gapStr",drto.gap());
		
		
		return "user/school/gsBuy";
	}

	@PostMapping("/user/school/gsFin")
	String gsFin(HttpSession session,Model mm, GsReserDTO drto) {
		String pid = (String)session.getAttribute("pid");
		mm.addAttribute("pid", pid);
		drto.setPid(pid);
		System.out.println("gsFin 진입");
		System.out.println("drto"+drto);
		
		Date start = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            start = sdf.parse(drto.getStartD());
        } catch (Exception e) {}
        // Calendar 객체 생성 및 기준 날짜 설정
        Calendar calendar = Calendar.getInstance();
        int ggap = drto.getGap();
        
        if(drto.getWeeks().equals("0")) {
        	System.out.println("일일권 진입~~~~~~~~");
        	int kk = start.getDay();
        	System.out.println("kk"+kk);
        	String weeks = "일월화수목금토";
        	System.out.println("weeks.split(\"\")[kk]:"+weeks.split("")[kk]);
        	drto.setWeeks(weeks.split("")[kk]);
		}
        grm.buy(drto);
        if(ggap ==0) {
        	drto.setOneDay(start);
        	grm.dayBuy(drto);
        }
		while(ggap!=0) {
			calendar.setTime(start);
			int wkChk = calendar.get(Calendar.DAY_OF_WEEK);
			String weeks = "토일월화수목금토";
			String kk = weeks.split("")[wkChk];
			if(drto.getWeeks().contains(kk)) {
				drto.setOneDay(start);
				grm.dayBuy(drto);
			}
			
	        calendar.add(Calendar.DAY_OF_MONTH, 1);

	        Date nextDate = calendar.getTime();
	        start = nextDate;
	        ggap = ggap -1;
		}
		
		mm.addAttribute("msg","결제완료");
		mm.addAttribute("goUrl", "/user/school");
		return "user/school/alert";
	}
}
