package com.sns.test;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sns.user.domain.User;
import com.sns.user.mapper.UserMapper;

@Controller
public class TestController {
	@Autowired
	UserMapper userMapper;
	
	@GetMapping("/test1")
	@ResponseBody
	public String test1() {
		return "<h2>hello world!</h2>";
	}
	
	@ResponseBody
	@GetMapping("/test2")
	public Map<String, Object> test2() {
		Map<String, Object> map = new HashMap<>();
		map.put("a", 111);
		map.put("bb", 22);
		map.put("ccc", "3");
		return map;
	}
	
	@GetMapping("/test3")
	public String test3() {
		return "test/test";
	}
	
	@ResponseBody
	@GetMapping("/test4")
	public List<User> test4() {
		return userMapper.selectUserList();
	}
}
