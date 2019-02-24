package edu.rit.iste500.dubai.RapidsCemeteryAPI.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import edu.rit.iste500.dubai.RapidsCemeteryAPI.enums.ExceptionEnum;
import edu.rit.iste500.dubai.RapidsCemeteryAPI.exception.APIException;
import edu.rit.iste500.dubai.RapidsCemeteryAPI.model.User;
import edu.rit.iste500.dubai.RapidsCemeteryAPI.request.ForgottenPasswordRequest;
import edu.rit.iste500.dubai.RapidsCemeteryAPI.request.LoginRequest;
import edu.rit.iste500.dubai.RapidsCemeteryAPI.response.LoginResponse;
import edu.rit.iste500.dubai.RapidsCemeteryAPI.response.ResetPasswordResponse;
import edu.rit.iste500.dubai.RapidsCemeteryAPI.security.SecurityConstants;
import edu.rit.iste500.dubai.RapidsCemeteryAPI.service.PasswordService;
import edu.rit.iste500.dubai.RapidsCemeteryAPI.service.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.swagger.annotations.Api;

@RestController
@RequestMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(tags = "login")
public class LoginController {

	@Autowired
	private UserService userService;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private PasswordService passwordService;

	@RequestMapping(value = "/login", method = RequestMethod.POST, produces = { "application/json" })
	public LoginResponse login(@RequestBody LoginRequest loginRequest) {

		LoginResponse response = new LoginResponse();

		try {
			validateLoginRequest(loginRequest);

			User user = userService.loadUserByUsername(loginRequest.getUsername());

			if (bCryptPasswordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
				String token = Jwts.builder().setSubject(loginRequest.getUsername())
						.setExpiration(new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME))
						.signWith(SignatureAlgorithm.HS512, SecurityConstants.ENCODER_SECRET.getBytes()).compact();
				response.setToken(SecurityConstants.TOKEN_PREFIX + token);

			} else {
				throw new APIException(ExceptionEnum.USER_NOT_FOUND);
			}

		} catch (Exception ex) {
			ex.printStackTrace();

		}

		return response;
	}

	@RequestMapping(value = "/login/forgottenPassword", method = RequestMethod.POST, produces = { "application/json" })
	public ResetPasswordResponse forgottenPassword(@RequestBody ForgottenPasswordRequest forgottenPasswordRequest) {

		ResetPasswordResponse response = new ResetPasswordResponse();

		try {
			validateForgottenPasswordRequest(forgottenPasswordRequest);

			User user = userService.loadUserByUsername(forgottenPasswordRequest.getUsername());
			if (user == null) {
				throw new APIException(ExceptionEnum.USER_NOT_FOUND);
			}

			boolean reset = passwordService.resetPassword(user);

			response.setValid(reset);

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return response;
	}

	private void validateLoginRequest(LoginRequest loginRequest) throws APIException {
		if (loginRequest == null || StringUtils.isEmpty(loginRequest.getUsername())
				|| StringUtils.isEmpty(loginRequest.getPassword())) {
			throw new APIException(ExceptionEnum.BAD_REQUEST);
		}

	}

	private void validateForgottenPasswordRequest(ForgottenPasswordRequest forgottenPasswordRequest)
			throws APIException {
		if (forgottenPasswordRequest == null || StringUtils.isEmpty(forgottenPasswordRequest.getUsername())) {
			throw new APIException(ExceptionEnum.BAD_REQUEST);
		}

	}
}
