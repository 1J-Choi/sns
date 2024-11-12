package com.sns.like.bo;

import org.springframework.stereotype.Service;

import com.sns.like.domain.Like;
import com.sns.like.mapper.LikeMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LikeBO {
	private final LikeMapper likeMapper;
	
	public String toggleLike(int postId, int userId) {
		Like like = likeMapper.selectLikeByPostIdAndUserId(postId, userId);
		
		if (like != null) {
			// like가 있다면 => 삭제
			likeMapper.deleteLike(postId, userId);
			return "좋아요 삭제 완료!";
		} else {
			// like가 없다면 => 생성
			likeMapper.insertLike(postId, userId);
			return "좋아요 생성 완료!";
		}
	}
	
	public int getLikeCount(int postId) {
		return likeMapper.selectLikeCount(postId);
	}
	
	public boolean getFilledLike(int postId, int userId) {
		Like like = likeMapper.selectLikeByPostIdAndUserId(postId, userId);
		if(like != null) {
			// like가 존재 => 꽉찬 하트 => true
			return true;
		} else {
			// like가 없음 => 빈 하트 => false
			return false;
		}
	}
}
