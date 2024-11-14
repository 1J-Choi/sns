package com.sns.post.bo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.sns.comment.bo.CommentBO;
import com.sns.common.FileManagerService;
import com.sns.like.bo.LikeBO;
import com.sns.post.entity.PostEntity;
import com.sns.post.repository.PostRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional // 중간에 오류가 발생할 시 transaction을 사용해 자동 rollback 함
public class PostBO {
	private final PostRepository postRepository;
	private final LikeBO likeBO;
	private final CommentBO commentBO;
	private final FileManagerService fileManager;
	
	public List<PostEntity> getPostList() {
		return postRepository.findByOrderByIdDesc();
	}
	
	public PostEntity addPost(int userId, String userLoginId, 
			String content, MultipartFile file) {
		String imagePath = fileManager.uploadFile(file, userLoginId);
		return postRepository.save(PostEntity.builder()
				.userId(userId)
				.content(content)
				.image(imagePath)
				.build());
	}
	
	public void deletePost(int postId, int userId) {
		// 삭제할 PostEntity 가져오기
		PostEntity post = postRepository.getByIdAndUserId(postId, userId);
		if(post == null) { // post가 존재하지 않을 시
			log.info("[글 삭제 실패] postId:{}, userId:{}", postId, userId);
			return;
		}
		log.info("[글 삭제] postId:{}, userId:{}", postId, userId);
		
		// 해당 글의 좋아요 삭제
		likeBO.deleteLikeByPostId(post.getId());
		
		// 해당 글의 댓글 삭제
		commentBO.deleteCommentByPostId(post.getId());
		
		// imagePath가 있을 시 해당 글의 이미지 파일 삭제
		if (post.getImage() != null) {			
			fileManager.deleteFile(post.getImage());
		}
		
		// 해당 글 삭제
		postRepository.delete(post); // 엔티티를 넘기지만 id(pk)로 삭제함
	}
}
