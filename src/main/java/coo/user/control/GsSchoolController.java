package coo.user.control;

import java.text.SimpleDateFormat;
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
		List<GsDogDTO> dogData = grm.dogArr(pid);
		System.out.println("dogSelect 진입");
		System.out.println("dogData"+dogData);
		mm.addAttribute("dogData", dogData);
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
	String detailSelect(Model mm, GsReserDTO drto) {
		System.out.println("detailSelect 진입");
		System.out.println("drto"+drto);
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
		grm.buy(drto);
		Date start = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            start = sdf.parse(drto.getStartD());
        } catch (Exception e) {}
        // Calendar 객체 생성 및 기준 날짜 설정
        Calendar calendar = Calendar.getInstance();
        int ggap = drto.getGap();
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
