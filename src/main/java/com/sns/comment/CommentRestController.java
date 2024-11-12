package com.sns.comment;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sns.comment.bo.CommentBO;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentRestController {
	
	private final CommentBO commentBO;
	
	/* 외부로 부터 생성자 주입 방식 <- 너무 귀찮음!
	 * @RequiredArgsConstructor로 한방에 가능
	public CommentRestController(CommentBO commentBO) {
		this.commentBO = commentBO;
	}
	*/
	
	@PostMapping("/create")
	public Map<String, Object> commentCreate(
			@RequestParam("content") String content, 
			@RequestParam("postId") int postId,
			HttpSession session) {
		// return 할 map 우선 선언
		Map<String, Object> result = new HashMap<>();
		
		// session에서 userId 가져오기 (비로그인시 댓글 작성 차단)
		Integer userId = (Integer) session.getAttribute("userId");
		if(userId == null) {
			result.put("code", 403);
			result.put("error_message", "로그인 후 댓글 작성이 가능합니다.");
			return result;
		}
		
		// db insert
		int rowCount = commentBO.addComment(postId, userId, content);
		
		// return 
		if (rowCount > 0) {
			result.put("code", 200);
			result.put("result", "성공");
		} else {
			result.put("code", 500);
			result.put("error_message", "댓글 등록 중 문제가 발생했습니다.");
		}
		return result;
	}
	
	@DeleteMapping("/delete")
	public Map<String, Object> deleteComment(
			@RequestParam("commentId") int commentId, 
			HttpSession session) {
		// 로그인 여부 확인
		Map<String, Object> result = new HashMap<>();
		Integer userId = (Integer)session.getAttribute("userId");
		if (userId == null) {
			result.put("code", 403);
			result.put("error_message", "로그인이 되지 않은 사용자 입니다.");
			return result;
		}
				
		// 삭제
		commentBO.deleteCommentById(commentId);
				
		// 응답값
		result.put("code", 200);
		result.put("result", "성공");
				
		return result;
	}
}
