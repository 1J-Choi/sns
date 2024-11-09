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
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class TimelineController {
	private final TimelineBO timelineBO;
	
	@GetMapping("/timeline")
	public String timeline(Model model) {
		List<CardDTO> cardList = timelineBO.generateCardList();
		model.addAttribute("cardList", cardList);
		
		return "timeline/timeline";
	}
}
