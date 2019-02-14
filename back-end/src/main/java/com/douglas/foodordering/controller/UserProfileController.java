package com.douglas.foodordering.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.douglas.foodordering.domain.UserProfile;
import com.douglas.foodordering.exceptionhandler.ControllerException;
import com.douglas.foodordering.service.UserProfileService;

@RestController
public class UserProfileController {

	@Autowired
	private UserProfileService userProfileService;
	
	@GetMapping("/api/user/{user_id}/profile")
	public ResponseEntity<UserProfile> getUserProfile(@PathVariable("user_id") Long userId) {
		Optional<UserProfile> userProfile = Optional.empty();
		
		try {
			userProfile = userProfileService.getUserProfile(userId);	
		} catch(Exception e) {
			throw new ControllerException(e.getMessage());
		}
		
		return ResponseEntity.of(userProfile);
	}
	
	@PutMapping("/api/user/{user_id}/profile")
	public ResponseEntity<UserProfile> updateUserProfile(@PathVariable("user_id") Long userId, @RequestBody UserProfile reqBody) {
		
		try {
			UserProfile profile = userProfileService.updateUserProfile(userId, reqBody);	

			return ResponseEntity.ok(profile);
		} catch(Exception e) {
			throw new ControllerException(e.getMessage());
		}
	}	
}
