package com.sns.user;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sns.common.EncryptUtills;
import com.sns.user.bo.UserBO;
import com.sns.user.entity.UserEntity;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/user")
public class UserRestController {
	@Autowired
	private UserBO userBO;
	
	/**
	 * 아이디 중복 확인
	 * @param loginId
	 * @return
	 */
	@GetMapping("/is-duplicate-id")
	public Map<String, Object> isDuplicateId(
			@RequestParam("loginId") String loginId) {
		// DB select
		UserEntity user = userBO.getUserEntityByLoginId(loginId);
		boolean isDuplicateId = false;
		if(user != null) {
			isDuplicateId = true;
		}
		
		// 응답값
		Map<String, Object> result = new HashMap<>();
		result.put("code", 200);
		result.put("is_duplicate_id", isDuplicateId);
		return result;
	}
	
	@PostMapping("/sign-up")
	public Map<String, Object> signUp(
			@RequestParam("loginId") String loginId,
			@RequestParam("password") String password,
			@RequestParam("name") String name,
			@RequestParam("email") String email) {
		// md5 알고리즘 - hashing (복호화가 안됨, 이미 보안 뚫림)
		// 같은 문자에 대해 같은 암호화를 하기에 복호화 불필요
		// aaaa = 74b8733745420d4d33f80c4663dc5e5
		String hashedPassword = EncryptUtills.md5(password);
		
		// db insert
		UserEntity user = userBO.addUser(loginId, hashedPassword, name, email);
		
		// 응답값
		Map<String, Object> result = new HashMap<>();
		if (user != null) {
			result.put("code", 200);
			result.put("result", "성공");
		} else {
			result.put("code", 500);
			result.put("error_message", "회원가입에 실패하였습니다.");
		}
		return result;
	}
	
	@PostMapping("/sign-in")
	public Map<String, Object> signIn(
			@RequestParam("loginId") String loginId,
			@RequestParam("password") String password,
			HttpServletRequest request) {
		// db select
		UserEntity user = userBO.getUser(loginId, password);
		
		// return
		Map<String, Object> result = new HashMap<>();
		if (user != null) {
			// 세션에 사용자 정보를 담는다.(사용자 각각)
			HttpSession session = request.getSession();
			session.setAttribute("userId", user.getId());
			session.setAttribute("userLoginId", user.getLoginId());
			session.setAttribute("userName", user.getName());
			result.put("code", 200);
			result.put("result", "성공");	
		} else {
			result.put("code", 300);
			result.put("error_message", "존재하지 않는 사용자 입니다.");
		}
		return result;
	}
}