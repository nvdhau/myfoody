package com.douglas.foodordering.controller;

import java.text.ParseException;
import java.util.HashMap;
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
import com.douglas.foodordering.utilities.Utils;
import com.fasterxml.jackson.databind.ObjectMapper;

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
	public ResponseEntity<UserProfile> updateUserProfile(@PathVariable("user_id") Long userId, @RequestBody String reqBody) {
		
		try {
			HashMap<String, Object> requestBody =
			        new ObjectMapper().readValue(reqBody, HashMap.class);			
			
			UserProfile inputProfile = convertToProfileObject(requestBody);
			
			UserProfile profile = userProfileService.updateUserProfile(userId, inputProfile);	
			
			return ResponseEntity.ok(profile);
		} catch(Exception e) {
			throw new ControllerException(e.getMessage());
		}
	}
	
	private UserProfile convertToProfileObject(HashMap<String, Object> requestBody) {
		UserProfile profile = new UserProfile();
		
		profile.setEmail(requestBody.get("email").toString());
		profile.setId(Long.valueOf(requestBody.get("id").toString()));
		profile.setFirstname(requestBody.get("firstname").toString());
		profile.setLastname(requestBody.get("lastname").toString());
		profile.setAddress(requestBody.get("address").toString());
		profile.setPhonenumbers(requestBody.get("phonenumbers").toString());			
		try {
			profile.setDateofbirth(Utils.parseStandardSQLDate(requestBody.get("dateofbirth").toString()));
		} catch (ParseException e) {
			throw new ControllerException(e.getMessage());
		}
		
		return profile;
	}	
}
