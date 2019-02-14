package com.douglas.foodordering.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douglas.foodordering.domain.User;
import com.douglas.foodordering.repository.UserRepositoty;
import com.douglas.foodordering.utilities.Constants;

@Service
public class UserService {

	@Autowired
	private UserRepositoty userRepos;
	
	@Autowired
	private UserProfileService userProfileService;
	
	public User createNewUser(String email, String password) throws Exception {
		// Create new user object
		User user = new User(email, password);		
		user.setRole(Constants.ROLE_USER);
		
		// Save to database
		User newUser = userRepos.save(user);
		
		// Create a new profile
		userProfileService.initializeUserProfile(email);
		
		return newUser;
	}
	
	public boolean checkIfEmailExist(String email) {
		return userRepos.existsByEmail(email);
	}
	
	public Optional<User> getUser(String email) {
		return userRepos.findByEmail(email);
	}
	
	public Optional<User> getUser(Long id) {
		return userRepos.findById(id);
	}
}
