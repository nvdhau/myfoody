package com.douglas.foodordering.service;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douglas.foodordering.domain.UserToken;
import com.douglas.foodordering.exceptionhandler.ControllerException;
import com.douglas.foodordering.repository.UserTokenRepository;
import com.douglas.foodordering.utilities.Constants;
import com.douglas.foodordering.utilities.Utils;

@Service
public class UserTokenService {

	@Autowired
	private UserTokenRepository tokenRepos;
	
	public Optional<UserToken> getToken(String token, String email) {
		return tokenRepos.findByTokenAndEmail(token, email);
	}
	
	public UserToken createToken(String token, String email) {
		UserToken userToken = null;
		
		try {
			userToken = new UserToken(
					token, 
					email, 
					new Timestamp(System.currentTimeMillis()), 
					new Timestamp((Utils.parseStandardDate(Constants.MAX_TIMESTAMP)).getTime())
				);
			
			userToken = tokenRepos.save(userToken);
		} catch (ParseException e) {
			throw new ControllerException(e.getMessage());
		}
		
		return userToken;
	}
	
	public UserToken expireToken(String token, String email) {
		Optional<UserToken> tokenObject = tokenRepos.findByTokenAndEmail(token, email);
		
		tokenObject.get().setExpired(new Timestamp(System.currentTimeMillis()));
		
		UserToken expiredToken = tokenRepos.save(tokenObject.get());
		
		return expiredToken;
	}
}
