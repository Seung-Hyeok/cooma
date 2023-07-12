/*package coo.user.control;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import coo.admin.model.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
public class BhMemChkController {
    private final MemberService memberService;

    @PostMapping("/emails/verification-requests")
    public ResponseEntity sendMessage(@RequestParam("email") @Valid String email) {
        memberService.sendCodeToEmail(email);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/emails/verifications")
    public ResponseEntity verificationEmail(@RequestParam("email") @Valid String email,
                                            @RequestParam("code") String authCode) {
        EmailVerificationResult response = memberService.verifiedCode(email, authCode);

        return new ResponseEntity<>(new SingleResponseDto<>(response), HttpStatus.OK);
    }
}
*/
package coo.user.control;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import coo.admin.db.BhDogMapper;
import coo.admin.db.BhDogsDTO;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;

@Controller
public class BhMemChkController {
	
	@Resource
	BhDogMapper dm;
	
//애견리스트
	@RequestMapping("/dogs")
	String bhDogList(Model mo, BhDogsDTO dog, HttpSession session) {
		List<BhDogsDTO> bhDogData = dm.bhDogList(dog);
		for (BhDogsDTO dto : bhDogData) {
			String name = dm.bhFindMemName(dto);
			dto.setPname(name);
			System.out.println("dto.getPname(): "+dto.getPname());
		}
		session.setAttribute("beforePage", "dogs");
		System.out.println("bhDogList() 진입");
		mo.addAttribute("bhDogData",bhDogData);
		return "admin/dogs/bhDogList";
	}
	
	
	
}