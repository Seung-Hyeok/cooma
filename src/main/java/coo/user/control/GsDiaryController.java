package coo.user.control;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import coo.user.db.GsDiaryMapper;
import coo.user.db.GsMyPageMapper;
import coo.user.db.GsReserDTO;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;

@Controller
public class GsDiaryController {
	
	@Resource
	GsDiaryMapper gdm;
	
	@GetMapping("/user/diary")
    public String showCalendar(HttpSession session,Model mm,GsReserDTO gdto) {
		String pid = "";
		if(session.getAttribute("pid") !=null) {
			pid = (String)session.getAttribute("pid");
			mm.addAttribute("pid", pid);
		}
		else {
			mm.addAttribute("msg","로그인 후 이용 가능합니다.");
			mm.addAttribute("goUrl","/user/log/login");
			return "user/log/alert";
		}
		gdto.setPid(pid);
		gdto.setDname("전체");
		
	
		String todd = "이번달 출석부";
		Calendar today= Calendar.getInstance(),befoMon = Calendar.getInstance();
		today.set(Calendar.DAY_OF_MONTH, 1);
		befoMon.set(Calendar.DAY_OF_MONTH, -1);
		String ymChk = today.get(Calendar.YEAR)+"";
		String befoChk = befoMon.get(Calendar.YEAR)+"";
		if(today.get(Calendar.MONTH)+1 >=10) {
			ymChk +="-"+(today.get(Calendar.MONTH)+1);
		}
		else {
			ymChk +="-0"+(today.get(Calendar.MONTH)+1);
		}
		
		
		List<GsReserDTO> show = gdm.showP(gdto);
		List<GsReserDTO> noShow = gdm.noShowP(gdto);
		List<GsReserDTO> future = gdm.futureP(gdto);
		List<GsReserDTO> showBuf = new ArrayList<GsReserDTO>();
		List<GsReserDTO> noShowBuf = new ArrayList<GsReserDTO>();
		List<GsReserDTO> futureBuf = new ArrayList<GsReserDTO>();
		System.out.println("ymChk2 : "+ymChk);
		
		
		for(GsReserDTO sh :show) {
			if(ymChk.equals(sh.oneYM())) {
				showBuf.add(sh);
			}
		}
		for(GsReserDTO ns :noShow) {
			if(ymChk.equals(ns.oneYM())) {
				noShowBuf.add(ns);
			}
		}
		for(GsReserDTO fu :future) {
			if(ymChk.equals(fu.oneYM())) {
				futureBuf.add(fu);
			}
		}
		
		mm.addAttribute("dnamess", gdm.dnames(gdto));
		mm.addAttribute("show", showBuf);
		mm.addAttribute("noShow", noShowBuf);
		mm.addAttribute("future", futureBuf);
		mm.addAttribute("datt", today.getActualMaximum(Calendar.DATE));
		mm.addAttribute("dattBefo", befoMon.getActualMaximum(Calendar.DATE));
		mm.addAttribute("old", today.get(Calendar.DAY_OF_WEEK));
		mm.addAttribute("todd", todd);
        return "user/diary/gsCalendar";
    }
	
	
	
	
	
	@PostMapping("/user/diary")
    public String postCalendar(HttpSession session,Model mm, String month,GsReserDTO gdto) {
		String pid = "";
		if(session.getAttribute("pid") !=null) {
			pid = (String)session.getAttribute("pid");
			mm.addAttribute("pid", pid);
		}
		else {
			mm.addAttribute("msg","로그인 후 이용 가능합니다.");
			mm.addAttribute("goUrl","/user/log/login");
			return "user/log/alert";
		}
		gdto.setPid(pid);
	
		Calendar today= Calendar.getInstance(),befoMon = Calendar.getInstance();
		
		int year = Integer.parseInt(month.split("-")[0]);
		int mon = Integer.parseInt(month.split("-")[1]);
		today.set(year,mon-1, 1);
		befoMon.set(year,mon-1, -1);
		String todd = year+"년 "+mon+"월 출석부";
		String ymChk = month;

		List<GsReserDTO> show = gdm.showP(gdto);
		List<GsReserDTO> noShow = gdm.noShowP(gdto);
		List<GsReserDTO> future = gdm.futureP(gdto);
		List<GsReserDTO> showBuf = new ArrayList<GsReserDTO>();
		List<GsReserDTO> noShowBuf = new ArrayList<GsReserDTO>();
		List<GsReserDTO> futureBuf = new ArrayList<GsReserDTO>();
		System.out.println("ymChk2 : "+ymChk);
		
		
		for(GsReserDTO sh :show) {
			if(ymChk.equals(sh.oneYM())) {
				showBuf.add(sh);
			}
		}
		for(GsReserDTO ns :noShow) {
			if(ymChk.equals(ns.oneYM())) {
				noShowBuf.add(ns);
			}
		}
		for(GsReserDTO fu :future) {
			if(ymChk.equals(fu.oneYM())) {
				futureBuf.add(fu);
			}
		}
		
		mm.addAttribute("dnamess", gdm.dnames(gdto));
		mm.addAttribute("show", showBuf);
		mm.addAttribute("noShow", noShowBuf);
		mm.addAttribute("future", futureBuf);
		mm.addAttribute("datt", today.getActualMaximum(Calendar.DATE));
		mm.addAttribute("dattBefo", befoMon.getActualMaximum(Calendar.DATE));
		mm.addAttribute("old", today.get(Calendar.DAY_OF_WEEK));
		mm.addAttribute("todd", todd);
        return "user/diary/gsCalendar";
    }
	 


}
