package com.sns.like.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.sns.like.domain.Like;

@Mapper
public interface LikeMapper {
	// call depth ctrl + alt + h
//	public int selectLikeByPostIdAndUserId(
//			@Param("postId") int postId, 
//			@Param("userId") int userId);
//	public int selectLikeCount(int postId);
	
	// 두 메소드를 합친 하나의 쿼리
	public int selectLikeCountByPostIdOrUserId(
			@Param("postId") int postId, 
			@Param("userId") Integer userId);
	
	public int deleteLike(
			@Param("postId") int postId, 
			@Param("userId") int userId);
	public int insertLike(
			@Param("postId") int postId, 
			@Param("userId") int userId);
}
