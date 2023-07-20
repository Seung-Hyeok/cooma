package coo.user.control;

import java.math.BigDecimal;
import java.math.RoundingMode;
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

import coo.user.db.GsMyPageMapper;
import coo.user.db.GsReserDTO;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;

@Controller
public class GsMyPageController {
	
	@Resource
	GsMyPageMapper gmm;
	
	
	@RequestMapping("/user/myPage/gsMyList")
	String myList(HttpSession session,Model mm) {
		String pid = (String)session.getAttribute("pid");
		mm.addAttribute("pid", pid);
		List<GsReserDTO> arr = gmm.reserArr(pid);
		mm.addAttribute("buyList", arr);
		System.out.println("myList 진입"+arr);
		return "user/myPage/gsMyList";
	}
	
	@RequestMapping("/user/myPage/gsMyOld")
	String myOld(HttpSession session,Model mm) {
		String pid = (String)session.getAttribute("pid");
		mm.addAttribute("pid", pid);
		List<GsReserDTO> arr = gmm.oldArr(pid);
		mm.addAttribute("oldList", arr);
		System.out.println("myOld 진입"+arr);
		return "user/myPage/gsMyOld";
	}
	
	@RequestMapping("/user/myPage/buyDetail/{reserNo}")
	String myDetail(HttpSession session,Model mm,GsReserDTO gdto) {
		String pid = (String)session.getAttribute("pid");
		mm.addAttribute("pid", pid);
		gdto = gmm.buyDetail(gdto);
		int all = gdto.getGap()/7*gdto.getWeeks().length();
		if(all ==0) {
			all=1;
		}
		int dng = gmm.attend(gdto);
		int dngAll = gmm.attendAll(gdto);
		System.out.println(dng+"/"+all);
		mm.addAttribute("myDetail", gdto);
		System.out.println("myDetail 진입"+gdto);
		  int att = (int) Math.round(100*dng/all);
		  int attAll = (int) Math.round(100*dngAll/all);
	
		mm.addAttribute("att",	att+"% ("+dng+"/"+all+")");
		mm.addAttribute("attAll",	attAll+"% ("+dngAll+"/"+all+")");
		return "user/myPage/gsBuyDetail";
	}
	@RequestMapping("/user/myPage/oldDetail/{reserNo}")
	String oldDetail(HttpSession session,Model mm,GsReserDTO gdto) {
		String pid = (String)session.getAttribute("pid");
		mm.addAttribute("pid", pid);
		gdto = gmm.buyDetail(gdto);
		int all = gdto.getGap()/7*gdto.getWeeks().length();
		if(all ==0) {
			all=1;
		}
		int dng = gmm.attend(gdto);
		int dngAll = gmm.attendAll(gdto);
		System.out.println(dng+"/"+all);
		mm.addAttribute("myDetail", gdto);
		System.out.println("myDetail 진입"+gdto);
		 int att = (int) Math.round(100*dng/all);
		  int attAll = (int) Math.round(100*dngAll/all);
	
		mm.addAttribute("att",	att+"% ("+dng+"/"+all+")");
		mm.addAttribute("attAll",	attAll+"% ("+dngAll+"/"+all+")");
		return "user/myPage/gsOldDetail";
	}
	
	@RequestMapping("/user/myPage/myAttend/{reserNo}")
	String myAttend(HttpSession session,Model mm,GsReserDTO gdto) {
		String pid = (String)session.getAttribute("pid");
		mm.addAttribute("pid", pid);
		System.out.println("show");
		mm.addAttribute("show", gmm.show(gdto));
		System.out.println("noShow");
		mm.addAttribute("noShow", gmm.noShow(gdto));
		System.out.println("future");
		mm.addAttribute("future", gmm.future(gdto));
		System.out.println("myAttend끝");
		return "user/myPage/gsMyAttend";
	}
	
	@GetMapping("/user/myPage/myRefund/{reserNo}")
	String myRefund(HttpSession session,Model mm,GsReserDTO gdto) {
		System.out.println("@GetMapping(\"/user/myPage/myRefund/{reserNo}\") 진입~~~~~~~~");
		String pid = (String)session.getAttribute("pid");
		mm.addAttribute("pid", pid);
		gdto = gmm.buyDetail(gdto);
		int all = gdto.getGap()/7*gdto.getWeeks().length();
		if(all ==0) {
			all=1;
		}
		System.out.println("all"+all);
		int dngAll = gmm.attendAll(gdto);
		
		System.out.println("myDetail 진입"+gdto);
		  int attAll = (int) Math.round(100*dngAll/all);
		Date today = new Date();
		Date start = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            start = sdf.parse(gdto.getStartD());
            System.out.println(start);
        } catch (Exception e) {}
		
        long diff = start.getTime() - today.getTime();
        int chk = (int)(diff / (24 * 60 * 60 * 1000));
        System.out.println("chk"+chk);
        if(chk>0) {
	        if(chk<=5) {
	        	gdto.setRefund(gdto.getTotFee()/5*4);
	        }
	        else if(chk<=10) {
	        	gdto.setRefund(gdto.getTotFee()/10*9);
	        }
	        else {
	        	gdto.setRefund(gdto.getTotFee());
	        System.out.println("ddddddddd");
	        }
        }
        else {
	    	if(attAll<=30) {
	        	gdto.setRefund(gdto.getTotFee()/5*2);
	    	}
	    	else if(attAll<=50) {
	        	gdto.setRefund(gdto.getTotFee()/5*1);
	    	}
	    	else{
	    		gdto.setRefund(0);
	    	
                
	    	}
        }
        System.out.println("gdto"+gdto);
        mm.addAttribute("attAll",	attAll+"% ("+dngAll+"/"+all+")");
        mm.addAttribute("myDetail", gdto);
		
		return "user/myPage/gsMyRef";
	}
	
	@PostMapping("/user/myPage/myRefund/{reserNo}")
	String myRefundfin(HttpSession session,Model mm,GsReserDTO gdto) {
		String pid = (String)session.getAttribute("pid");
		mm.addAttribute("pid", pid);
		gdto.setPid(pid);

		if(gdto.getRefund()==0) {
			mm.addAttribute("msg","이용권 진행률이 50%를 초과하여 환불이 불가합니다");
            mm.addAttribute("goUrl", "/user/myPage/buyDetail/"+gdto.getReserNo());
    		
    		return "user/myPage/alert";
		}
		
		
        gmm.dayRefun(gdto);
		int refun = gmm.myRefun(gdto);
		System.out.println("myRefund 진입"+refun);
		
		
		
		mm.addAttribute("msg", "환불되었습니다");
		mm.addAttribute("goUrl", "/user/myPage/oldDetail/"+gdto.getReserNo());
		
		return "user/myPage/alert";
	}
	
	 


}
