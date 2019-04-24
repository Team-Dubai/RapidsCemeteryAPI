package edu.rit.iste500.dubai.RapidsCemeteryAPI.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import edu.rit.iste500.dubai.RapidsCemeteryAPI.service.TokenService;
import io.jsonwebtoken.JwtException;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

	@Autowired
	TokenService tokenService;

	public JWTAuthorizationFilter(AuthenticationManager authenticationManager) {
		super(authenticationManager);
	}

	@SuppressWarnings("finally")
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		try {
			String header = request.getHeader(SecurityConstants.HEADER_STRING);
			if (header == null || !header.startsWith(SecurityConstants.TOKEN_PREFIX)) {
				chain.doFilter(request, response);
				return;
			}

			Authentication authenticationToken = tokenService.verifyToken(request);

			SecurityContextHolder.getContext().setAuthentication(authenticationToken);

			chain.doFilter(request, response);
		} catch (JwtException ex) {
			SecurityContextHolder.getContext().setAuthentication(null);
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			ex.printStackTrace();
		} finally {
			return;
		}

	}

}
