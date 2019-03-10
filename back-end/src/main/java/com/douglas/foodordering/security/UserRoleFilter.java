package com.douglas.foodordering.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.douglas.foodordering.domain.User;
import com.douglas.foodordering.service.UserService;
import com.douglas.foodordering.utilities.Constants;

@Component
public class UserRoleFilter {

	@Autowired
	private UserService userService;
	
	public boolean doFilter(Authentication authentication, long id) {
		String user = authentication.getPrincipal().toString();
		
		Optional<User> currentUser = userService.getUser(user);
		
		if(currentUser.isPresent()) {
			return currentUser.get().getRole().equals(Constants.ROLE_USER) && 
					currentUser.get().getId() == id;
		}
		
		return false;
	}

}
