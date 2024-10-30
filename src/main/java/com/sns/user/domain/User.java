package com.sns.user.domain;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class User {
	private int id;
	private int loginId;
	private String password;
	private String name;
	private String email;
	private String imagePath;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
}
