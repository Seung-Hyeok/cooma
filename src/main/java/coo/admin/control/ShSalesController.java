package coo.admin.control;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import coo.admin.db.ShReservationDTO;
import coo.admin.db.ShReservationMapper;
import coo.admin.db.ShSalesMapper;
import coo.admin.model.PData;
import jakarta.annotation.Resource;

@Controller
public class ShSalesController {
	
	@Resource
	ShSalesMapper sm;
	
	@RequestMapping("/admin/sales")
	String sales(Model mm, PData pd) {
		
		System.out.println("sales 진입");
		
		List<ShReservationDTO> mainData = sm.salesList(pd);
		System.out.println(mainData);
		
		mm.addAttribute("mainData", mainData);
		
		
		return "admin/sales/sales";
	}

}
