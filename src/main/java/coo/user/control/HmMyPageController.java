package coo.user.control;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import coo.user.db.HmFileData;
import coo.user.db.HmDogsDTO;
import coo.user.db.HmMemberDTO;
import coo.user.db.HmMyPageMapper;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class HmMyPageController {
	@Resource
	HmMyPageMapper mp;
	
	//마이페이지///////////////////////////////////////
		@RequestMapping("/user/myPage/memPage")
		String myPage(HttpSession session, Model mm) {
			
			String pid = (String)session.getAttribute("pid");
			mm.addAttribute("pid", pid);
			HmMemberDTO myData =  mp.my(pid);
			mm.addAttribute("myData", myData);
			
			SimpleDateFormat format = new SimpleDateFormat("yyyy년 MM월 dd일");
			mm.addAttribute("birth",format.format(myData.getBirth()));
			
			return "user/myPage/memPage";
		}
	
		//회원정보수정///////////////////////////////////////
		@GetMapping("/user/myPage/modifyForm")
		String modifyForm(HttpSession session, Model mm, HmMemberDTO dto) {
			
			String pid = (String)session.getAttribute("pid");
			mm.addAttribute("pid", pid);
			HmMemberDTO myData =  mp.my(pid);
			mm.addAttribute("myData", myData);
			
			SimpleDateFormat format = new SimpleDateFormat("yyyy년 MM월 dd일");
			mm.addAttribute("birth",format.format(myData.getBirth()));
			
			return "user/myPage/modifyForm";
		}  
		
		@PostMapping("/user/myPage/modifyForm")
		String modifyComplete(HttpSession session, Model mm, HmMemberDTO dto) {
			
			String pid = (String)session.getAttribute("pid");
			dto.setPid(pid);
			int cnt = mp.modify(dto);
			
			String msg = "비밀번호가 일치하지 않습니다.";
			String goUrl = "/user/myPage/modifyForm";
			
			if(cnt>0) {
				msg = "회원정보가 수정되었습니다.";
				goUrl = "/user/myPage/memPage";
				
				HmMemberDTO memData = mp.logchk(dto);
				session.setAttribute("pname", memData.getPname());
			}
			
			mm.addAttribute("msg", msg);
			mm.addAttribute("goUrl", goUrl);
			
			return "user/myPage/alert";
		}
	
	//회원탈퇴///////////////////////////////////////
	@GetMapping("/user/myPage/delete")
	String delete(HttpSession session, Model mm, HmMemberDTO dto) {
		String pid = (String)session.getAttribute("pid");
		mm.addAttribute("pid", pid);
		
		return "user/myPage/delete";
	}
	
	@PostMapping("/user/myPage/delete")
	String deleteComplete(HttpSession session, Model mm, HmMemberDTO dto, HmDogsDTO dog) {
		
		String pid = (String)session.getAttribute("pid");
		dto.setPid(pid);
		dog.setPid(pid);
		
		int reserChk =  mp.myReser(dto);
		
		if(reserChk >= 1) {
			mm.addAttribute("msg", "회원탈퇴는 예약기간 종료 또는 환불 이후 가능합니다");
			mm.addAttribute("goUrl", "/user/myPage/gsMyList");
		} else {
		    	
	    	HmMemberDTO memData = mp.my(pid);
			
			int cnt = mp.delete(dto);
			
			String msg = "비밀번호가 일치하지 않습니다.";
			String goUrl = "/user/myPage/delete";
			
			if(cnt>0) {
				msg = "회원탈퇴가 완료되었습니다.";
				goUrl = "/user";
				dog.setDname(memData.getDog1());
				mp.dogDelete(dog);
				dog.setDname(memData.getDog2());
				mp.dogDelete(dog);
				dog.setDname(memData.getDog3());
				mp.dogDelete(dog);
				session.invalidate();
			}
			mm.addAttribute("msg", msg);
			mm.addAttribute("goUrl", goUrl);
	    }
		
		return "user/myPage/alert";
	}
	
	//비번수정///////////////////////////////////////
	@GetMapping("/user/myPage/pwChange")
	String pwChange(HttpSession session, Model mm, HmMemberDTO dto) {
		String pid = (String)session.getAttribute("pid");
		mm.addAttribute("pid", pid);
		
		return "user/myPage/pwChange";
	}
	
	@PostMapping("/user/myPage/pwChange")
	String pwChangeComplete(HttpSession session, Model mm, HmMemberDTO dto) {
		
		String pid = (String)session.getAttribute("pid");
		dto.setPid(pid);
		//HmMemberDTO memData = lm.pwchk(dto);
		int cnt = mp.pwChange(dto);
		
		String msg = "비밀번호가 일치하지 않습니다.";
		String goUrl = "/user/myPage/pwChange";
		
		if(cnt>0) {
			msg = "비밀번호가 변경되었습니다.";
			goUrl = "/user/myPage/memPage";
		}
		
		mm.addAttribute("msg", msg);
		mm.addAttribute("goUrl", goUrl);
		
		return "user/myPage/alert";
	}
	
	//애견등록///////////////////////////////////////
		@GetMapping("/user/myPage/dogJoinForm")
		String dogJoinForm(HttpSession session, Model mm,HmDogsDTO dto) {
			String pid = (String)session.getAttribute("pid");
			mm.addAttribute("pid", pid);
			return "user/myPage/dogJoinForm";
		}
		
		@PostMapping("/user/myPage/dogJoinForm")
		String dogJoinComplete(HttpServletRequest request, HttpSession session, Model mm, HmDogsDTO dto, HmMemberDTO member, HmFileData fd) {
			String pid = (String)session.getAttribute("pid");
			dto.setPid(pid);
			int cnt = mp.dogNameChk(dto);
			
			if(fd.getDogimg().getContentType().startsWith("image/")) {
				HmMemberDTO memData = mp.my(pid);
				
				if(memData.getDog1()==null || memData.getDog1().equals("")) {
					memData.setDog1(dto.getDname());
				}
				else if(memData.getDog2()==null || memData.getDog2().equals("")) {
					memData.setDog2(dto.getDname());
				}
				else if(memData.getDog3()==null || memData.getDog3().equals("")) {
					memData.setDog3(dto.getDname());
				}
				
				if(1>cnt) {
					String res = fileSave(fd.getDogimg(), request);
		            dto.setPhoto(res);
		            mm.addAttribute("msg","강아지 등록이 완료되었습니다.");
					mm.addAttribute("goUrl","/user/myPage/dogList");
					mp.dnameset(memData);
					mp.insertDog(dto);
				} else {
					mm.addAttribute("msg","회원당 동일한 강아지이름은 사용하실 수 없습니다.");
					mm.addAttribute("goUrl","/user/myPage/dogJoinForm");
				}
				
			} 
			
			else {
				mm.addAttribute("msg","파일은 이미지 파일만 업로드할 수 있습니다.");
				mm.addAttribute("goUrl","/user/myPage/dogJoinForm");
				return "user/myPage/alert";
			}
			
			return "user/myPage/alert";
		}
	
	//애견리스트///////////////////////////////////////
	@RequestMapping("/user/myPage/dogList")
	String dogList(HttpSession session, Model mm, HmDogsDTO dto) {
		String pid = (String)session.getAttribute("pid");
		mm.addAttribute("pid", pid);
		dto.setPid(pid);
		List<HmDogsDTO> dogData = mp.dogList(dto);
		//System.out.println("mainData:"+mainData);
		System.out.println(dogData);
		mm.addAttribute("dogData", dogData);
		return "user/myPage/dogList";
	}
	
	//애견디테일///////////////////////////////////////
	@RequestMapping("/user/myPage/dogDetail/{dname}")
	String dogDetail(HttpSession session, Model mm, HmDogsDTO dto) {
		String pid = (String)session.getAttribute("pid");
		mm.addAttribute("pid", pid);
		dto.setPid(pid);
		HmDogsDTO dogData = mp.dogDetail(dto);
		mm.addAttribute("dogData",dogData);
		
		LocalDate currentDate = LocalDate.now();
	    LocalDate birthday = LocalDate.of(dogData.getDyear(), dogData.getDmonth(), 1);
	    long months = ChronoUnit.MONTHS.between(birthday, currentDate);
	    int dYear = (int)months/12;
	    int dMonth = (int)months%12;
	    mm.addAttribute("dYear", dYear);
	    mm.addAttribute("dMonth", dMonth);
		
		session.setAttribute("photo", dogData.getPhoto());
		return "user/myPage/dogDetail";
	}
	
	//애견수정///////////////////////////////////////
	@GetMapping("/user/myPage/dogModify/{dname}")
	String dogModify(HttpSession session, Model mm, HmDogsDTO dto) {
		String pid = (String)session.getAttribute("pid");
		mm.addAttribute("pid", pid);
		dto.setPid(pid);
		System.out.println("photo1"+session.getAttribute("photo"));
		mm.addAttribute("dogData", mp.dogDetail(dto));
		return "user/myPage/dogModify";
	}
	
	@PostMapping("/user/myPage/dogModify/{dname}")
	String dogModifyComplete(HttpServletRequest request, HttpSession session, Model mm, HmDogsDTO dto, HmFileData fd) {
		String pid = (String)session.getAttribute("pid");
		String photo = (String)session.getAttribute("photo");
		System.out.println("photo2"+photo);
		
		//System.out.println("dto.getPhoto()2"+dto.getPhoto());
		dto.setPid(pid);
		if(fd.getDogimg().isEmpty()) {
			dto.setPhoto(photo);
		}
		else {
			String res = fileSave(fd.getDogimg(), request);
            dto.setPhoto(res);
		}
		
		mp.dogModify(dto);
		//HmMemberDTO memData = mp.my(pid);
		/*
		if(memData.getDog1().equals(dname)) {
			memData.setDog1(dto.getDname());
			mp.dnameset(memData);
		}
		else if(memData.getDog2().equals(dname)) {
			memData.setDog2(dto.getDname());
			mp.dnameset(memData);
		}
		else if(memData.getDog3().equals(dname)) {
			memData.setDog3(dto.getDname());
			mp.dnameset(memData);
		}*/
		
		mm.addAttribute("msg", "애견정보 수정이 완료되었습니다.");
		mm.addAttribute("goUrl", "/user/myPage/dogDetail/"+dto.getDname());
		
		return "user/myPage/alert";
	}
	//애견삭제///////////////////////////////////////
	@RequestMapping("/user/myPage/dogDelete/{dname}")
	String dogDelete(HttpSession session, Model mm, HmDogsDTO dto) {
		System.out.println("ddddd");
		String pid = (String)session.getAttribute("pid");
		dto.setPid(pid);
		
		HmDogsDTO dogData = mp.dogDetail(dto);
		
		int reserChk =  mp.dogReser(dogData);
			
	    if (reserChk >= 1) {
	    	mm.addAttribute("msg", "등록해제는 예약기간 종료 또는 환불 이후 가능합니다");
			mm.addAttribute("goUrl", "/user/myPage/gsMyList");
			
	    } else {
			
			HmMemberDTO memData = mp.my(pid);
			
			if(memData.getDog1().equals(dto.getDname())) {
				memData.setDog1("");
			}
			else if(memData.getDog2().equals(dto.getDname())) {
				memData.setDog2("");
			}
			else if(memData.getDog3().equals(dto.getDname())) {
				memData.setDog3("");
			}
			
			int cnt = mp.mydogs(pid);
			
			if (cnt <= 1) {
				mm.addAttribute("msg", "애견은 한마리 이상 등록되어야합니다");
				mm.addAttribute("goUrl", "/user/myPage/dogDetail/" + dto.getDname());
			} else if (cnt > 1) {
				mp.dnameset(memData);
				mp.dogDelete(dto);
				mm.addAttribute("msg", "애견등록이 해제되었습니다.");
				mm.addAttribute("goUrl", "/user/myPage/dogList");
			} else {
				mm.addAttribute("msg", "에러코드:500 고객센터에 문의하세요.");
				mm.addAttribute("goUrl", "/user/myPage/dogDetail/" + dto.getDname());
			}
			
	    }
		
		return "user/myPage/alert";
	}
	
	//애견3마리 체크///////////////////////////////////////
	@RequestMapping("/user/myPage/dogJoinChk")
	String dogJoinChk(HttpSession session, Model mm, HmMemberDTO dto) {
		String pid = (String)session.getAttribute("pid");
		
		HmMemberDTO myData =  mp.my(pid);
		
		String msg = "애견등록은 최대 3마리까지 가능합니다.";
		String goUrl = "/user/myPage/dogList";
		
		if(myData.getDog1()==null || myData.getDog1().equals("")
				|| myData.getDog2()==null || myData.getDog2().equals("")
				||myData.getDog3()==null || myData.getDog3().equals("")) {
			msg = "애견등록페이지로 이동합니다.";
			goUrl = "/user/myPage/dogJoinForm";
		}
		
		mm.addAttribute("msg", msg);
		mm.addAttribute("goUrl", goUrl);
		
		return "user/myPage/alert";
	}
	
	//사진저장///////////////////////////////////////
    String fileSave(MultipartFile mf,    HttpServletRequest request) {
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
