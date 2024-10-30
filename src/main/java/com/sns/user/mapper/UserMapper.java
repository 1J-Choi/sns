package com.sns.user.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.sns.user.domain.User;

@Mapper
public interface UserMapper {
	public List<User> selectUserList();
}
