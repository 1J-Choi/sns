package com.sns.timeline;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.sns.comment.bo.CommentBO;
import com.sns.comment.domain.Comment;
import com.sns.post.bo.PostBO;
import com.sns.post.entity.PostEntity;
import com.sns.timeline.bo.TimelineBO;
import com.sns.timeline.domain.CardDTO;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class TimelineController {
	private final TimelineBO timelineBO;
	
	@GetMapping("/timeline")
	public String timeline(Model model, 
			HttpSession session) {
		Integer userId = (Integer) session.getAttribute("userId");
		if(userId == null) { // 비로그인 시
			userId = 0; // 존재하지 않는 userId인 0으로 setting
		}
		List<CardDTO> cardList = timelineBO.generateCardList(userId);
		model.addAttribute("cardList", cardList);
		
		return "timeline/timeline";
	}
}
