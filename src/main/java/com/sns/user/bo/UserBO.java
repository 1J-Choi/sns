package com.sns.user.bo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sns.common.EncryptUtills;
import com.sns.user.entity.UserEntity;
import com.sns.user.repository.UserRepository;

@Service
public class UserBO {
	@Autowired
	private UserRepository userRepository;
	
	// 컨트롤러에게
		// input: loginId
		// output: UserEntity
		public UserEntity getUserEntityByLoginId(String loginId) {
			return userRepository.findByLoginId(loginId);
		}
		
		// input: 파라미터 4개
		// output: UserEntity
		public UserEntity addUser(String loginId, String password, String name, String email) {
			return userRepository.save(
					UserEntity.builder()
					.loginId(loginId)
					.password(password)
					.name(name)
					.email(email)
					.build());
		}
		
		// input: 파라미터 2개
		// output: UserEntity
		public UserEntity getUser(String loginId, String password) {
			// 비밀번호 해싱
			String hashedPassword = EncryptUtills.md5(password);
			
			return userRepository.findByLoginIdAndPassword(loginId, hashedPassword);
		}
}
