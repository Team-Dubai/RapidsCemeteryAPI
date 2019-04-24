package edu.rit.iste500.dubai.RapidsCemeteryAPI.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResetPasswordRequest {

	private String username;
	private String oldPassword;
	private String password;
	private String confirmPassword;
}
