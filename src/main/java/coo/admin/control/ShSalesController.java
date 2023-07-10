package coo.admin.control;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import coo.admin.db.ShReservationDTO;
import coo.admin.db.ShReservationMapper;
import coo.admin.db.ShSalesMapper;
import coo.admin.db.ShStatisticsRepository;
import coo.admin.model.PData;
import jakarta.annotation.Resource;

@Controller
public class ShSalesController {
	
	@Resource
	ShSalesMapper sm;
	
	@Autowired
	private ShStatisticsRepository sr;
	
	@RequestMapping("/admin/sales")
	String sales(Model mm, PData pd) {
		
		System.out.println("sales 진입");
		
		List<ShReservationDTO> mainData = sm.salesList(pd);
		
		int cnt = 0; // 결제 건수
		int tf = 0; // 결제 금액
		int rf = 0; // 환불 금액
		int tot = 0; // 총 액
		for(ShReservationDTO e : mainData) {
			cnt++;
			tf += e.getTotFee();
			rf += e.getRefund();
		}
		
		tot = tf-rf;
		
		System.out.println(mainData);
		
		System.out.println(pd.getStartD());
		System.out.println(pd.getEndD());
		mm.addAttribute("cnt", cnt);
		mm.addAttribute("mainData", mainData);
		mm.addAttribute("tf", tf);
		mm.addAttribute("rf", rf);
		mm.addAttribute("tot", tot);
		
		
		return "admin/sales/sales";
	}
	
	@GetMapping("/admin/statistics")
	String statistic(Model mm) {
		
		System.out.println("statistics 진입");
		
		List<ShReservationDTO> DataList = sr.getSalesData();
        mm.addAttribute("DataList", DataList);
		
		return "admin/sales/statistics";
	}

}
