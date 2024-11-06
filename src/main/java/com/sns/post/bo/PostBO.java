package com.sns.post.bo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.sns.common.FileManagerService;
import com.sns.post.entity.PostEntity;
import com.sns.post.repository.PostRepository;

@Service
public class PostBO {
	@Autowired
	private PostRepository postRepository;
	@Autowired
	private FileManagerService fileManager;
	
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
}
