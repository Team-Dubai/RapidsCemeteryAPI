package edu.rit.iste500.dubai.RapidsCemeteryAPI.security;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import edu.rit.iste500.dubai.RapidsCemeteryAPI.model.User;

public class SpringSecurityUtil {

	public static User getCurrentUser() {
		SecurityContext context = SecurityContextHolder.getContext();

		if (context != null && context.getAuthentication() != null) {
			Object principal = context.getAuthentication().getPrincipal();

			if (principal != null && principal instanceof User) {
				return (User) principal;
			}
		}

		return null;
	}

	public static boolean userExists() {
		return getCurrentUser() != null;
	}

	public static String getCurrentUsername() {
		String username = "unauthenticated";
		User user = getCurrentUser();

		if (user != null) {
			username = user.getUsername();
		}

		return username;
	}

}
