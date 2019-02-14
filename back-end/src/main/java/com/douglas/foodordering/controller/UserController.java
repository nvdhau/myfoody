package com.douglas.foodordering.controller;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.douglas.foodordering.domain.User;
import com.douglas.foodordering.exceptionhandler.ControllerException;
import com.douglas.foodordering.service.UserService;

import lombok.Getter;

@RestController
public class UserController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@PostMapping("/signup")
	public ResponseEntity<?> signup(@Valid @RequestBody UserRequest req) {
		if(userService.checkIfEmailExist(req.getEmail())) {
			throw new ControllerException("Email already used");
		}
		
		try {
			User newUser = userService.createNewUser(req.getEmail(), passwordEncoder.encode(req.getPassword()));

			Map<String, String> resBody = new HashMap<>();
			resBody.put("email", newUser.getEmail());
			resBody.put("role", newUser.getRole());
			
			return ResponseEntity.ok(resBody);
		} catch(Exception e) {
			throw new ControllerException(e.getMessage());
		}
	}
	
	private static class UserRequest {
		@NotBlank
		@Email
		@Getter
		private String email;
		
		@NotBlank
		@Getter
		private String password;		
	}	
}
