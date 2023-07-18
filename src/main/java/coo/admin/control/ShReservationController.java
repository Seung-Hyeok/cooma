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
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class ShReservationController {
	
	@Resource
	ShReservationMapper rm;
	
	@RequestMapping("/admin/reservation/{nowPage}")
	String reser(Model mm, PData pd, HttpSession session) {
		String pid = (String)session.getAttribute("pid");
		mm.addAttribute("pid", pid);
		
		pd.setTotal(rm.total(pd));
		List<ShReservationDTO> mainData = rm.list(pd);
		
		System.out.println(mainData);
		System.out.println("reservation 진입");
		
		System.out.println(pd.getTotalPage());
		System.out.println(pd.getStart());
		System.out.println(pd.getStartPage());
		System.out.println(pd.getEndPage());
		
		System.out.println(pd);
		
		System.out.println("list:"+pd.getSch());
		//System.out.println("cnt:"+dto.getCnt());
		
		mm.addAttribute("mainData", mainData);
		mm.addAttribute("pd", pd);
		
		return "admin/reser/reservation";
	}
	
	@RequestMapping("/admin/endreservation/{nowPage}")
	String endreser(Model mm, PData pd, HttpServletRequest request, HttpSession session) {
		String pid = (String)session.getAttribute("pid");
		mm.addAttribute("pid", pid);
		
		System.out.println("request"+request.getParameter("sch"));
		pd.setTotal(rm.endtotal(pd));
		List<ShReservationDTO> mainData = rm.endlist(pd);
		
		System.out.println(mainData);
		System.out.println("endreservation 진입");
		
		System.out.println("list:"+pd.getSch());
		//System.out.println("cnt:"+dto.getCnt());
		
		mm.addAttribute("mainData", mainData);
		mm.addAttribute("pd", pd);
		System.out.println(pd);
		
		return "admin/reser/endreservation";
	}
	@RequestMapping("/admin/endreservation/detail/{reserNo}")
    String endreserdetail(Model mm, ShReservationDTO dto, HttpSession session) {
		String pid = (String)session.getAttribute("pid");
		mm.addAttribute("pid", pid);
		
        System.out.println(rm.detail(dto));
        mm.addAttribute("mainData", rm.detail(dto));

        System.out.println(dto.getEdu());

        System.out.println("endreserdetail 진입");

        return "admin/reser/endreserdetail";
    }
	@RequestMapping("/admin/reservation/detail/{reserNo}")
	String detail(Model mm, ShReservationDTO dto, HttpSession session) {
		String pid = (String)session.getAttribute("pid");
		mm.addAttribute("pid", pid);
		
		
		System.out.println(rm.detail(dto));
		mm.addAttribute("mainData", rm.detail(dto));
		
		System.out.println(dto.getEdu());
		
		System.out.println("detail 진입");
		
		return "admin/reser/detail";
	}
	
	@GetMapping("/admin/modify/{reserNo}")
	String modifyForm(Model mm, ShReservationDTO dto, HttpSession session) {
		String pid = (String)session.getAttribute("pid");
		mm.addAttribute("pid", pid);
		
		System.out.println("modifyForm 진입");
		mm.addAttribute("mainData", rm.detail(dto));
		return "admin/reser/modifyForm";
	}
	
	@PostMapping("/admin/modify/{reserNo}")
	String modifyComplete(Model mm, ShReservationDTO dto, HttpSession session) {
		String pid = (String)session.getAttribute("pid");
		mm.addAttribute("pid", pid);
		
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
	String delete(Model mm, ShReservationDTO dto, HttpSession session) {
		String pid = (String)session.getAttribute("pid");
		mm.addAttribute("pid", pid);
		
		System.out.println("delete 진입");
		dto = rm.detail(dto);
		System.out.println(dto);
		Date today = new Date();
		
        long diff = dto.getStartD().getTime() - today.getTime();
        int chk = (int)(diff / (24 * 60 * 60 * 1000));
        
        if(chk>0) {
	        if(chk<=1) {
	        	dto.setRefund(dto.getTotFee()/5*4);
	        }
	        else if(chk<=5) {
	        	dto.setRefund(dto.getTotFee()/10*9);
	        }
	        else {
	        	dto.setRefund(dto.getTotFee());
	        }
        }
        else {
	    	if(-chk <= dto.getGap()/10*3) {
	        	dto.setRefund(dto.getTotFee()/5*2);
	    	}
	    	else if(-chk <= dto.getGap()/2) {
	        	dto.setRefund(dto.getTotFee()/5*1);
	    	}
	    	else if(-chk < dto.getGap()) {
	    		dto.setRefund(0);
	    	}
        }
        int cnt = rm.delete(dto);
		System.out.println("삭제갯수:"+cnt);
		mm.addAttribute("msg","삭제되었습니다.");
		mm.addAttribute("goUrl", "/admin/reservation/1");
		return "admin/reser/alert";
	}
	
	
	
	@RequestMapping("/admin/refund/{nowPage}")
	String refund(Model mm, PData pd, HttpSession session) {
		String pid = (String)session.getAttribute("pid");
		mm.addAttribute("pid", pid);
		
		pd.setTotal(rm.refundtotal(pd));
		List<ShReservationDTO> mainData = rm.refundList(pd);
		
		System.out.println(mainData);
		System.out.println("refund 진입");
		
		mm.addAttribute("mainData", mainData);
		mm.addAttribute("pd", pd);
		
		System.out.println(pd);
		System.out.println("환불내역 페이지 가기직전!!!");
		return "admin/reser/refund";
	}

}
