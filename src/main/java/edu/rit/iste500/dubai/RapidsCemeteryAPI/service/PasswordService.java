package edu.rit.iste500.dubai.RapidsCemeteryAPI.service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import edu.rit.iste500.dubai.RapidsCemeteryAPI.enums.ExceptionEnum;
import edu.rit.iste500.dubai.RapidsCemeteryAPI.exception.APIException;
import edu.rit.iste500.dubai.RapidsCemeteryAPI.model.User;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PasswordService {

	@Value("${defaultPasswordLength:15}")
	private int defaultPasswordLength;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private MailService mailService;

	@Autowired
	private UserService userService;

	public static boolean isLegalPassword(final String pass) {
		boolean result = true;

		List<Pattern> patterns = new ArrayList<Pattern>();

		patterns.add(Pattern.compile(".*[A-Z].*"));
		patterns.add(Pattern.compile(".*[a-z].*"));
		patterns.add(Pattern.compile(".*\\d.*"));
		patterns.add(Pattern.compile(".*[^A-Za-z0-9].*"));

		for (Pattern pattern : patterns) {
			if (!pattern.matcher(pass).matches()) {
				result = false;
				break;
			}
		}

		return result;
	}

	public boolean resetPassword(final User user) throws Exception {
		log.debug("resetPassword start");

		boolean success = false;

		String password = generateTemporaryPassword();

		String environment = "";

		String toEmail = user.getEmail();

		if (!StringUtils.isBlank(toEmail)) {
			mailService.sendMail(null, toEmail, "Forgoten password email", "Your temporary password is: " + password,
					false);

			user.setPassword(encodePassword(password, user.getSalt()));
			user.setPasswordReset(true);
			userService.save(user);

			success = true;
		} else {
			throw new APIException(ExceptionEnum.NO_EMAIL);
		}

		log.debug("resetPassword end");
		return success;
	}

	public String generateTemporaryPassword() {
		return RandomStringUtils.random(defaultPasswordLength,
				"!#$%&*+-=@^_123456789ABCDEFGHJKLMNPQRSTUVWXYZabcdefghjkmnpqrstuvwxyz");
	}

	public void encodePassword(final User user, final String password) {
		user.setPassword(bCryptPasswordEncoder.encode(password));
		user.setPasswordReset(false);

	}

	public String encodePassword(final String password, final String salt) {
		return bCryptPasswordEncoder.encode(password);
	}

	public boolean checkPassword(final User user, final String password) {
		boolean passwordEqual = false;

		String encodedPassword = bCryptPasswordEncoder.encode(password);
		passwordEqual = encodedPassword.equals(user.getPassword());

		return passwordEqual;
	}

	public String newPassword(final User user, boolean sendMail) throws Exception {
		String password = generateTemporaryPassword();
		encodePassword(user, password);

		return user.getPassword();
	}

	public boolean isValidPassword(final String password) {
		boolean ret = true;

		if (!StringUtils.isNotBlank(password)) {
			ret = false;
		} else if (password.length() < 8) {
			ret = false;
		} else if (!isLegalPassword(password)) {
			ret = false;
		}

		return ret;
	}

}
