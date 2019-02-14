package com.douglas.foodordering.security;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.douglas.foodordering.domain.User;
import com.douglas.foodordering.service.UserService;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserService userService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User currentUser = userService.getUser(username)
				.orElseThrow(() -> new NoSuchElementException("User not found"));
		
		return new org.springframework.security.core.userdetails.User(
				username, currentUser.getPassword()
				,true, true, true, true
				,AuthorityUtils.createAuthorityList(currentUser.getRole()));
	}

}
