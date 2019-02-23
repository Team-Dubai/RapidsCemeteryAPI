package edu.rit.iste500.dubai.RapidsCemeteryAPI.security;

public class SecurityConstants {

	public static final long EXPIRATION_TIME = 1000L * 60L * 720L;
	public static final String TOKEN_PREFIX = "TKN ";
	public static final String HEADER_STRING = "JWT";
	public static final String ERROR_STRING = "ERROR";
	public static final String ENCODER_SECRET = "WDIdgdgWVJfQp24V";
	public static final String SIGN_UP_URL = "/users/singup";
	public static final String SWAGGER_LOGIN_URL = "/login";
	public static final String FORGOTTEN_PASSWORD = "/login/forgottenPassword";

}
