package com.sns.comment.bo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sns.comment.domain.Comment;
import com.sns.comment.domain.CommentDTO;
import com.sns.comment.mapper.CommentMapper;
import com.sns.user.bo.UserBO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommentBO {
	private final CommentMapper commentMapper;
	private final UserBO userBO;
	
	public int addComment(int postId, int userId, String content) {
		return commentMapper.insertComment(postId, userId, content);
	}
	
	public List<Comment> getCommentList(){
		return commentMapper.selectCommentList();
	}
	
	public List<CommentDTO> generateCommentList(int postId){
		List<Comment> comments = commentMapper.selectCommentListByPostId(postId);
		
		List<CommentDTO> commentDTOList = new ArrayList<>();
		
		for(Comment comment : comments) {
			CommentDTO commentDTO = new CommentDTO();
			commentDTO.setComment(comment);
			commentDTO.setUser(userBO.getUserEntityById(comment.getUserId()));
			commentDTOList.add(commentDTO);
		}
		
		return commentDTOList;
	}
	
	public void deleteCommentById(int commentId) {
		commentMapper.deleteCommentById(commentId);
	}
}
