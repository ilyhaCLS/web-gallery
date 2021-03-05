package com.ilyha.secsite.imgConfig;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		exposeDirectory("user-photos",registry);
	}
	
	private void exposeDirectory(String dir, ResourceHandlerRegistry registry) {
		
		Path uploadDir = Paths.get(dir);
		String uploadPath = uploadDir.toFile().getAbsolutePath();
		
		registry.addResourceHandler("/"+dir+"/**").addResourceLocations("file:/"+uploadPath+"/");
	}
}