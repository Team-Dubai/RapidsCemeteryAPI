package edu.rit.iste500.dubai.RapidsCemeteryAPI.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import edu.rit.iste500.dubai.RapidsCemeteryAPI.model.User;
import edu.rit.iste500.dubai.RapidsCemeteryAPI.security.SecurityConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Service
public class TokenService {

	@Autowired
	private UserService userService;

	public Authentication verifyToken(HttpServletRequest request) {

		String token = request.getHeader(SecurityConstants.HEADER_STRING);
		if (token != null && !token.isEmpty()) {

			final User user = parseUserFromToken(token);

			if (user != null) {
				return new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
			}

		}
		return null;

	}

	private User parseUserFromToken(String token) {

		Claims claims = Jwts.parser().setSigningKey(SecurityConstants.ENCODER_SECRET.getBytes())
				.parseClaimsJws(token.replace(SecurityConstants.TOKEN_PREFIX, "")).getBody();

		String username = null;

		if (claims.getSubject() != null) {
			username = claims.getSubject();
		}

		User user = null;

		try {
			if (username != null) {
				user = userService.loadUserByUsername(username);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return user;
	}

}
