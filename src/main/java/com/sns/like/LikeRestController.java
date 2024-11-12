package com.sns.like;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.sns.like.bo.LikeBO;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class LikeRestController {
	private final LikeBO likeBO;
	
	// GET: /like/3
	@GetMapping("/like/{postId}")
	public Map<String, Object> likeToggle(
			@PathVariable(name = "postId") int postId, 
			HttpSession session) {
		// 로그인 여부 확인
		Map<String, Object> result = new HashMap<>();
		Integer userId = (Integer)session.getAttribute("userId");
		if (userId == null) {
			result.put("code", 403);
			result.put("error_message", "로그인이 되지 않은 사용자 입니다.");
			return result;
		}
		
		// toggle 요청 -> BO
		String successMessage = likeBO.toggleLike(postId, userId);
		
		// return
		result.put("code", 200);
		result.put("result", successMessage);
		return result;
	}
}
