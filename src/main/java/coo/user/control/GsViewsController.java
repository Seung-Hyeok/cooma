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
public class GsViewsController {
	
	@Resource
	GsReserMapper grm;
	
	
	@RequestMapping("/user/build")
	String dogSelect(HttpSession session,Model mm) {
		String pid = (String)session.getAttribute("pid");
		mm.addAttribute("pid", pid);
	
		return "user/spBuild/gsBuild";
	}
	@GetMapping("/user/special")
	String packSelect(HttpSession session,Model mm, GsDogDTO gdto) {
		String pid = (String)session.getAttribute("pid");
		mm.addAttribute("pid", pid);
	
		return "user/spBuild/special";
	}
	
}
