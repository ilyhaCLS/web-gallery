package com.ilyha.secsite.Controllers;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.ilyha.secsite.auth.UserPrincipal;
import com.ilyha.secsite.auth.UserRepository;
import com.ilyha.secsite.model.User;

@Controller
public class TemplateController {
	
	@Autowired
	UserRepository repository;
	
	@Autowired
	PasswordEncoder encoder;
	
	@GetMapping("/gallery")
	public String galeryPage(Model m, HttpServletRequest req, Authentication auth) {
		
		ArrayList<Path> list = new ArrayList<Path>();
		try {
			Files.walkFileTree(Paths.get("user-photos"), new SimpleFileVisitor<Path>() {
				
				public FileVisitResult visitFile(Path path, BasicFileAttributes attribs) {
					System.out.println(path.toString());
					list.add(path);
					return FileVisitResult.CONTINUE;
				}
			});
		}catch(IOException e) {
			System.out.println("IO Exception for: "+req.getRemoteAddr());
		}
		list.sort((Path p1, Path p2)->{
			try {
				return Files.readAttributes(p2, BasicFileAttributes.class).creationTime().compareTo(Files.readAttributes(p1, BasicFileAttributes.class).creationTime());
			}catch(IOException e) {
				System.out.println("ERROR WHILE SORTING");
				return 0;
			}
		});
		m.addAttribute("files", list);
		m.addAttribute("username", auth.getName());
		return "gallery";
	}
	
	@PostMapping("/addImg")
	public String addImg(@RequestParam("fname") MultipartFile file, Model m, HttpServletResponse res) {
		try {
			String extension = new Tika().detect(file.getBytes());
			System.out.println(extension);
			if (extension.startsWith("image/")) {
				Random r = new Random(System.currentTimeMillis());

				BufferedInputStream imgIn = new BufferedInputStream(file.getInputStream(), 65536); //64kb

				Path uploadPath = Paths.get("user-photos/" + r.nextInt());
				if (!Files.exists(uploadPath)) {
					Files.createDirectories(uploadPath);
				}

				Path filePath = uploadPath.resolve(StringUtils.cleanPath(file.getOriginalFilename()));
				Files.copy(imgIn, filePath, StandardCopyOption.REPLACE_EXISTING);

				imgIn.close();
				
				res.sendRedirect("/gallery");
				
				return "/gallery";
			}else {
				m.addAttribute("error_msg", "Invalid datatype. Only images allowed");
				return "/error";
			}
		} catch (IOException e) {
			m.addAttribute("error_msg", "IOException");
			return "/error";
		}
	}
	
	@GetMapping("/me")
	public String me(HttpServletResponse res, Authentication auth, Model m) {
		UserPrincipal up  = (UserPrincipal)auth.getPrincipal();
		String data = String.format("|| Username: %s || Authorities: %s || Is account Active: %s || Account Registration Date: %s", 
				up.getUsername(), up.getAuthorities().toString(),up.isEnabled(), up.getRegDate());
		m.addAttribute("data", data);
		return "/me";
	}
	
	
	@PostMapping("/register")
	public String registration(@RequestParam("username") String username, @RequestParam("password") String password, Model m) {
		
		Integer id_check = repository.checkIfRegistered(username);
		
		if(id_check == null) {
				User us = new User(username,encoder.encode(password));
				repository.saveAndFlush(us);
			return "redirect:/";
		}else {
			m.addAttribute("error_msg", "That username has already been taken! Try another");
			return "/error";
		}
	}
	 
}