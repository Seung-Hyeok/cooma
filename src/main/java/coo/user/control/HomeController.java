package coo.user.control;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import coo.user.db.HmDaybydayDTO;
import coo.user.db.HmLoginMapper;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {
	
	@Resource
	HmLoginMapper lm;
	
	@RequestMapping("/user")
	String home(HttpSession session, Model mm, HmDaybydayDTO dbd) {
		System.out.println("home 실행");
		String pid = (String)session.getAttribute("pid");
		mm.addAttribute("pid", pid);
		
		List<HmDaybydayDTO> dbdDto = lm.checkDay(pid);
		
		LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formatToday = today.format(formatter);
		
        List<String> dayStr = new ArrayList<>();
        List<String> dogName = new ArrayList<>();
        
        for (HmDaybydayDTO dd : dbdDto) {
        	Date date = dd.getOneDay();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String formattedDate = sdf.format(date);
            dayStr.add(formattedDate);
		}
		
        String todayInfo = "";
        int cnt = 0;
        for (String st : dayStr) {
        	if(st.equals(formatToday)) {
        		dogName.add(dbdDto.get(cnt).getDname());
        		
        		todayInfo = "오늘은 "+dogName.toString().replaceAll("[\\[\\]]", "")+" 등원날입니다.";
    		}
        	cnt ++;
		}
		
        mm.addAttribute("today",todayInfo); //링크추가
		Object pname = session.getAttribute("pname");
		
		if(pname!=null) {
			mm.addAttribute("msg",pname+"님 반갑습니다.");
		}
		
		return "user/userHome/home";
	}
}
