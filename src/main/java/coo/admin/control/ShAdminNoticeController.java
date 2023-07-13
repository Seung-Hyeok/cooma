package coo.admin.control;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import coo.admin.db.ShNoticeDTO;
import coo.admin.db.ShNoticeMapper;
import coo.admin.db.ShReservationDTO;
import coo.admin.db.ShReservationMapper;
import coo.admin.model.PData;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class ShAdminNoticeController {
	
	@Resource
	ShNoticeMapper nm;
	
	@RequestMapping("/admin/notice/{nowPage}")
	String notice(Model mm, PData pd) {
		pd.setTotal(nm.ntotal(pd));
		System.out.println("관리자 공지사항 진입");
		List<ShNoticeDTO> mainData = nm.list(pd);
		
		mm.addAttribute("mainData", mainData);
		mm.addAttribute("pd", pd);
		System.out.println(pd);
		
		return "admin/notice/adnotice";
	}
	
	
	@RequestMapping("/admin/notice/detail/{num}")
	String detail(Model mm, ShNoticeDTO dto) {
		
		System.out.println("detail 진입");
		
		nm.up(dto);
		mm.addAttribute("mainData", nm.detail(dto));
		
		System.out.println(nm.detail(dto).getPhotoFile());
		
		
		if(nm.detail(dto).getPhotoFile().isEmpty()) {
			mm.addAttribute("dd",false);
		}else {
			mm.addAttribute("dd", true);
		}
		
		
		
		System.out.println("돼?");
		return "admin/notice/noticedetail";
	}
	
	@GetMapping("/admin/notice/modify/{num}")
	String modifyForm(Model mm, ShNoticeDTO dto) {
		System.out.println("modifyForm 진입");
		System.out.println(dto.getPhotoFile());
		mm.addAttribute("mainData", nm.detail(dto));
		System.out.println(nm.detail(dto).getPhotoFile());
		return "admin/notice/modifyForm";
	}
	
	@PostMapping("/admin/notice/modify/{num}")
	String modifyComplete(Model mm, ShNoticeDTO dto, HttpSession session) {
		System.out.println("modipost 진입");
		System.out.println(dto.getPhotoFile());
		
		System.out.println(session.getAttribute("photo"));
		System.out.println(nm.detail(dto).getPhotoFile());
		
		if(dto.getPhotoFile().isEmpty()) { 
			dto.setPhotoFile(nm.detail(dto).getPhotoFile());
		}
		
		int cnt = nm.modify(dto);
			
		
		String msg = "에러";
		String goUrl = "/admin/notice/modify/"+dto.getNum();
		if(cnt>0) {
			msg = "수정되었습니다.";
			goUrl = "/admin/notice/detail/"+dto.getNum();
		}
		System.out.println("수정갯수:"+cnt);
		mm.addAttribute("msg", msg);
		mm.addAttribute("goUrl", goUrl);
		return "admin/reser/alert";
	}
	
	@RequestMapping("/admin/notice/delete/{num}")
	String delete(Model mm, ShNoticeDTO dto) {
		
		System.out.println("delete 진입");
		
		int cnt = nm.delete(dto);
		System.out.println("삭제갯수:"+cnt);
		mm.addAttribute("msg","삭제되었습니다.");
		mm.addAttribute("goUrl", "/admin/notice/1");
		
		return "admin/reser/alert";
	}
	
	
	
	@GetMapping("/admin/notice/insert")
	String insert(ShNoticeDTO dto) {
		
		System.out.println("inserform 진입");
		
		return "admin/notice/insertForm";
	}
	
	@PostMapping("/admin/notice/insert")
	String insertComplete(Model mm, ShNoticeDTO dto, HttpServletRequest request) {
		
		System.out.println("insertComplete 진입");
		
		System.out.println(dto);
		System.out.println(dto.getNum());
		System.out.println(dto.getNoticeImg());
		System.out.println(dto.getNoticeImg().getOriginalFilename());
		
		if(dto.getNoticeImg().getOriginalFilename().isEmpty()) {
			dto.setPhotoFile("");
		}else {
			String res = fileSave(dto.getNoticeImg(), request);
	        dto.setPhotoFile(res);
		}
		
		
		nm.insert(dto);
		mm.addAttribute("msg", "입력되었습니다.");
		mm.addAttribute("goUrl", "/admin/notice/detail/"+dto.getNum());
		
		return "admin/reser/alert";
	}
	
	
	
	 String fileSave(MultipartFile mf, HttpServletRequest request) {
	        String path = request.getServletContext().getRealPath("dimg")+"/";
	        //System.out.println(path);
	        String res = mf.getOriginalFilename();
	        File ff = new File(path+res);
	        int pos = res.lastIndexOf(".");
	        String fName = res.substring(0,pos);
	        String ext = res.substring(pos);
	        int  no = 0;
	        while(ff.exists()) {
	            no++;
	            res = fName+no+ext;
	            ff = new File(path+res);
	        }
	        try {
	            FileOutputStream fos = new FileOutputStream(ff);
	            fos.write(mf.getBytes());
	            fos.close();
	        } catch (Exception e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }

	        return res;
	    }
	
	
	
}