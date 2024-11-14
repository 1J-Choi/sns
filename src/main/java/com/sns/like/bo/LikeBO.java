package com.sns.like.bo;

import org.springframework.stereotype.Service;

import com.sns.like.domain.Like;
import com.sns.like.mapper.LikeMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LikeBO {
	private final LikeMapper likeMapper;
	
	public void toggleLike(int postId, int userId) {
		if (likeMapper.selectLikeCountByPostIdOrUserId(postId, userId) > 0) {
			// like가 있다면 => 삭제
			likeMapper.deleteLike(postId, userId);
			// return "좋아요 삭제 완료!";
		} else {
			// like가 없다면 => 생성
			likeMapper.insertLike(postId, userId);
			// return "좋아요 생성 완료!";
		}
	}
	
	public int getLikeCount(int postId) {
		return likeMapper.selectLikeCountByPostIdOrUserId(postId, null);
	}
	
	public boolean getFilledLike(int postId, Integer userId) {
//		if(userId != null && likeMapper.selectLikeByPostIdAndUserId(postId, userId) > 0) {
//			// 로그인 & like가 존재 => 꽉찬 하트 => true
//			return true;
//		} else {
//			// 비로그인 => 빈하트 => false
//			// 로그인 & like가 없음 => 빈 하트 => false
//			return false;
//		}
		if(userId == null) {
			return false;
		}
		
		int likeCount = likeMapper.selectLikeCountByPostIdOrUserId(postId, userId);
		return likeCount > 0;
	}
	
	public void deleteLikeByPostId(int postId) {
		likeMapper.deleteLike(postId, null);
	}
}
