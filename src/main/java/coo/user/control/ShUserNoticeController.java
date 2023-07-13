package coo.user.control;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import coo.admin.db.ShNoticeDTO;
import coo.admin.db.ShNoticeMapper;
import coo.admin.model.PData;
import jakarta.annotation.Resource;

@Controller
public class ShUserNoticeController {
	
	@Resource
	ShNoticeMapper nm;
	
	@RequestMapping("/user/notice/{nowPage}")
	String notice(Model mm, PData pd) {
		pd.setTotal(nm.ntotal(pd));
		System.out.println("유저공지사항 진입");
		List<ShNoticeDTO> mainData = nm.list(pd);
		
		mm.addAttribute("mainData", mainData);
		mm.addAttribute("pd", pd);
		
		System.out.println(pd);
		
		return "user/notice/usernotice";
	}
	
	@RequestMapping("/user/notice/detail/{num}")
	String detail(Model mm, ShNoticeDTO dto) {
		
		System.out.println("userdetail 진입");
		
		nm.up(dto);
		mm.addAttribute("mainData", nm.detail(dto));
		
		if(nm.detail(dto).getPhotoFile().isEmpty()) {
			mm.addAttribute("dd",false);
		}else {
			mm.addAttribute("dd", true);
		}
		
		System.out.println(nm.detail(dto).getPhotoFile());
		
		return "user/notice/noticedetail";
	}
	
}
