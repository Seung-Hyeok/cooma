package coo.user.control;

import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import coo.user.db.HmQnaDTO;

@Service
public class HmQnaChk {
	
	public boolean hasErrors(HmQnaDTO dto, BindingResult bRes) {
		
		System.out.println("큐엔에이 hasErrors() 진입");
		
		if(bRes.hasErrors()) {
			System.out.println("큐엔에이 에러");
			return true;
		}
		return false;
	}
}
