package com.sns.timeline.bo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.sns.comment.bo.CommentBO;
import com.sns.post.bo.PostBO;
import com.sns.post.entity.PostEntity;
import com.sns.timeline.domain.CardDTO;
import com.sns.user.bo.UserBO;
import com.sns.user.entity.UserEntity;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TimelineBO {
	private final PostBO postBO;
	private final UserBO userBO;
	private final CommentBO commentBO;
	
	// input: X
	// output: List<CardDTO>
	public List<CardDTO> generateCardList() {
		List<CardDTO> cardList = new ArrayList<>();
		
		// 글 목록 가져옴
		List<PostEntity> postList = postBO.getPostList();
		
		// 글 1개 => CardDTO로 변환 반복문
		for(PostEntity post : postList) {
			CardDTO card = new CardDTO();
			card.setPost(post);
			card.setUser(userBO.getUserEntityById(post.getUserId()));
			card.setCommentDTOList(commentBO.generateCommentList(post.getId()));
			cardList.add(card);
		}
		
		return cardList;
	}
}
