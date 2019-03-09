package com.douglas.foodordering.security;

import java.util.Collections;
import java.util.Date;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.douglas.foodordering.domain.UserToken;
import com.douglas.foodordering.service.UserTokenService;
import com.douglas.foodordering.utilities.Utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class AuthenticationService {
	static final long EXPIRATIONTIME = 864_000_00 * 365; // 1 year in milliseconds
	static final String SIGNINGKEY = "EBAAF7FC9E9D26B25BAF321471200A44";
	static final String PREFIX = "Bearer";
	
	private static UserTokenService tokenService;
	
	@Autowired
	private void setTokenService(UserTokenService inputTokenService) {
		tokenService = inputTokenService;
	}
	
	// Add token to Authorization header
	static public void addToken(HttpServletResponse res, String username) {
		// Create java token
		String JwtToken = PREFIX + " " + Jwts.builder().setSubject(username)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATIONTIME))
				.signWith(SignatureAlgorithm.HS512, SIGNINGKEY).compact();
		
		// Add token to database
		tokenService.createToken(JwtToken, username);
		
		// Add token to response header
		res.addHeader("Authorization", JwtToken);
		res.addHeader("Access-Control-Expose-Headers", "Authorization");
	}

	// Get token from Authorization header
	static public Authentication getAuthentication(HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		if (token != null) {
			String user = getUserNameFromJwtToken(token);
			if (user != null) {
				Optional<UserToken> userToken = tokenService.getToken(user, token);
				if(userToken.isPresent() && Utils.isExpiredRecord(userToken.get().getExpired())) {
					return new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList());
				}
				
			}
		}
		return null;
	}
	
	static public String getUserNameFromJwtToken(String token) {
		String user = Jwts.parser().setSigningKey(SIGNINGKEY).parseClaimsJws(token.replace(PREFIX, "")).getBody()
				.getSubject();
		
		return user;
	}
}
