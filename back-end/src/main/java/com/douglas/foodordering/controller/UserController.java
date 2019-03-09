package com.douglas.foodordering.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.douglas.foodordering.domain.User;
import com.douglas.foodordering.exceptionhandler.ControllerException;
import com.douglas.foodordering.security.AuthenticationService;
import com.douglas.foodordering.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Getter;

@RestController
public class UserController {

	@Autowired
	private UserService userService;
	
	@PostMapping("/signup")
	public ResponseEntity<?> signup(@Valid @RequestBody UserRequest req) {
		if(userService.checkIfEmailExist(req.getEmail())) {
			throw new ControllerException("Email already used");
		}
		
		try {
			User newUser = userService.createNewUser(req.getEmail(), req.getPassword());

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
	
	@PutMapping("/api/user/{user_id}/password")
	public ResponseEntity<?> changePassword(@PathVariable("user_id") Long userId, HttpServletRequest request) {
		
		try {		
			String reqBody = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
			
			@SuppressWarnings("unchecked")
			HashMap<String, Object> requestBody =
			        new ObjectMapper().readValue(reqBody, HashMap.class);	
			
			String oldPassword = requestBody.get("oldpassword").toString();
			String newPassword = requestBody.get("newpassword").toString();
			
			User user = userService.changeUserPassword(userId, oldPassword, newPassword);
			
			Map<String, String> resBody = new HashMap<>();
			if(user != null) {
				AuthenticationService.expireToken(request);
				
				resBody.put("email", user.getEmail());
				resBody.put("message", "Password updated");
				return ResponseEntity.ok(resBody);
			} else { 
				resBody.put("message", "Change password failed");
				return ResponseEntity.badRequest().body(resBody);
			}
			
		} catch(Exception e) {
			throw new ControllerException(e.getMessage());
		}
	}
}
