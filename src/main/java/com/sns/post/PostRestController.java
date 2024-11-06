package com.sns.post;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sns.post.bo.PostBO;
import com.sns.post.entity.PostEntity;

import jakarta.servlet.http.HttpSession;

@RestController
public class PostRestController {
	@Autowired
	private PostBO postBO;
	
	/**
	 * 게시글 등록
	 * @param content
	 * @param file
	 * @param session
	 * @return
	 */
	@PostMapping("/timeline/create-post")
	public Map<String, Object> createPost(
			@RequestParam(value = "content", required = false) String content,
			@RequestParam("file") MultipartFile file,
			HttpSession session
			) {
		// db 등록 및 이미지 저장을 위한 userId, userLoginId 받기
		Integer userId = (Integer) session.getAttribute("userId");
		String userLoginId = (String) session.getAttribute("userLoginId");
		
		// userId 검증후 return 하기 위해 result를 미리 선언한다.
		Map<String, Object> result = new HashMap<>();
		// userId가 있는지 확인
		if(userId == null) {
			result.put("code", 401);
			result.put("error_message", "로그인한 유저만 글을 게시할 수 있습니다.");
			return result;
		}

		// db insert
		PostEntity post = postBO.addPost(userId, userLoginId, content, file);
		
		// result return
		if (post != null) {
			result.put("code", 200);
			result.put("result", "성공");
		} else {
			result.put("code", 500);
			result.put("result", "글 게시 도중 문제가 발생하였습니다.");
		}
		return result;
	}
}
