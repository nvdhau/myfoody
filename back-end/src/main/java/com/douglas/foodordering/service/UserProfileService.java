package com.douglas.foodordering.service;

import java.sql.Timestamp;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douglas.foodordering.domain.User;
import com.douglas.foodordering.domain.UserProfile;
import com.douglas.foodordering.repository.UserProfileRepository;

@Service
public class UserProfileService {

	@Autowired
	private UserProfileRepository userProfileRepos;
	
	@Autowired
	private UserService userService;
	
	public Optional<UserProfile> getUserProfile(String email) {
		return userProfileRepos.findByEmail(email);
	}
	
	public Optional<UserProfile> getUserProfile(Long userId) throws Exception {
		Optional<User> user = userService.getUser(userId);
		if(!user.isPresent())
			throw new Exception("User(id = " + userId + ") not found");
		
		String email = user.get().getEmail();
		
		return userProfileRepos.findByEmail(email);
	}
	
	public UserProfile initializeUserProfile(String email) throws Exception {
		if(getUserProfile(email).isPresent())
			throw new Exception("Profile already exist for user " + email);
		
		UserProfile profile = new UserProfile(email, new Timestamp(System.currentTimeMillis()));
		
		return userProfileRepos.save(profile);
	}
	
	public UserProfile updateUserProfile(Long userId, UserProfile updatedProfile) throws Exception {
		UserProfile profile = getUserProfile(userId).get();
		
		profile.setFirstname(updatedProfile.getFirstname());
		profile.setLastname(updatedProfile.getLastname());
		profile.setAddress(updatedProfile.getAddress());
		profile.setPhonenumbers(updatedProfile.getPhonenumbers());
		profile.setDateofbirth(updatedProfile.getDateofbirth());
		
		return userProfileRepos.save(profile);
	}
}
