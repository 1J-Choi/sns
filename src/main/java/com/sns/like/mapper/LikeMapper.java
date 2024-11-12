package com.sns.like.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.sns.like.domain.Like;

@Mapper
public interface LikeMapper {
	public Like selectLikeByPostIdAndUserId(
			@Param("postId") int postId, 
			@Param("userId") int userId);
	public int deleteLike(
			@Param("postId") int postId, 
			@Param("userId") int userId);
	public int insertLike(
			@Param("postId") int postId, 
			@Param("userId") int userId);
	public int selectLikeCount(int postId);
}
