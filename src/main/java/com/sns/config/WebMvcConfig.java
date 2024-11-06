package com.sns.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.sns.common.FileManagerService;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer{
	// 예언된 이미지 path와 서버에 업로드 된 실제 이미지를 매핑
		@Override
		public void addResourceHandlers(ResourceHandlerRegistry registry) {
			registry
			.addResourceHandler("/images/**") // path http://localhost/images/aaaa_1730889218888/wonjae.jpg
			.addResourceLocations("file:///" + FileManagerService.FILE_UPLOAD_PATH);
		}
}
