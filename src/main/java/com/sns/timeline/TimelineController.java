package com.sns.timeline;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.sns.post.bo.PostBO;
import com.sns.post.entity.PostEntity;

import io.swagger.v3.oas.annotations.responses.ApiResponse;

@Controller
public class TimelineController {
	@Autowired
	private PostBO postBO;
	
	@GetMapping("/timeline")
	public String timeline(Model model) {
		// db select
		List<PostEntity> postList = postBO.getPostList();
		
		// model
		model.addAttribute("postList", postList);
		
		return "timeline/timeline";
	}
}
