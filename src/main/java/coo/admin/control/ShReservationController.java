package coo.admin.control;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import coo.admin.db.ShReservationDTO;
import coo.admin.db.ShReservationMapper;
import coo.admin.model.PData;
import jakarta.annotation.Resource;

@Controller
public class ShReservationController {
	
	@Resource
	ShReservationMapper rm;
	
	@RequestMapping("/admin/reservation/{nowPage}")
	String reser(Model mm, PData pd) {
		List<ShReservationDTO> mainData = rm.list(pd);
		
		System.out.println(mainData);
		System.out.println("reservation 진입");
		
		System.out.println("list:"+pd.getSch());
		//System.out.println("cnt:"+dto.getCnt());
		
		mm.addAttribute("mainData", mainData);
		
		return "admin/reser/reservation";
	}
	
	@RequestMapping("/admin/endreservation/{nowPage}")
	String endreser(Model mm, PData pd) {
		List<ShReservationDTO> mainData = rm.endlist(pd);
		
		System.out.println(mainData);
		System.out.println("endreservation 진입");
		
		System.out.println("list:"+pd.getSch());
		//System.out.println("cnt:"+dto.getCnt());
		
		mm.addAttribute("mainData", mainData);
		
		return "admin/reser/endreservation";
	}
	
	@RequestMapping("/admin/endreservation/detail/{reserNo}")
	String endreserdetail(Model mm, ShReservationDTO dto) {
		System.out.println(rm.detail(dto));
		mm.addAttribute("mainData", rm.detail(dto));
		
		System.out.println(dto.getEdu());
		
		System.out.println("endreserdetail 진입");
		
		return "admin/reser/endreserdetail";
	}
	
	@RequestMapping("/admin/reservation/detail/{reserNo}")
	String detail(Model mm, ShReservationDTO dto) {
		
		System.out.println(rm.detail(dto));
		mm.addAttribute("mainData", rm.detail(dto));
		
		System.out.println(dto.getEdu());
		
		System.out.println("detail 진입");
		
		return "admin/reser/detail";
	}
	
	@GetMapping("/admin/modify/{reserNo}")
	String modifyForm(Model mm, ShReservationDTO dto) {
		System.out.println("modifyForm 진입");
		mm.addAttribute("mainData", rm.detail(dto));
		return "admin/reser/modifyForm";
	}
	
	@PostMapping("/admin/modify/{reserNo}")
	String modifyComplete(Model mm, ShReservationDTO dto) {
		System.out.println("modipost 진입");
		int cnt = rm.modify(dto);
		String msg = "에러";
		String goUrl = "/admin/modify/"+dto.getReserNo();
		if(cnt>0) {
			msg = "수정되었습니다.";
			goUrl = "/admin/reservation/detail/"+dto.getReserNo();
		}
		System.out.println("수정갯수:"+cnt);
		mm.addAttribute("msg", msg);
		mm.addAttribute("goUrl", goUrl);
		return "admin/reser/alert";
	}
	
	@RequestMapping("/admin/delete/{reserNo}")
	String delete(Model mm, ShReservationDTO dto) {
		System.out.println("delete 진입");
		int cnt = rm.delete(dto);
		
		
		System.out.println("삭제갯수:"+cnt);
		mm.addAttribute("msg","삭제되었습니다.");
		mm.addAttribute("goUrl", "/admin/reservation/1");
		return "admin/reser/alert";
	}
	
	
	
	@RequestMapping("/admin/refund")
	String refund(Model mm, PData pd) {
		
		List<ShReservationDTO> mainData = rm.refundList(pd);
		
		System.out.println(mainData);
		System.out.println("refund 진입");
		
		mm.addAttribute("mainData", mainData);
		
		return "admin/reser/refund";
	}

}
