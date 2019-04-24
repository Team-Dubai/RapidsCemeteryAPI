package edu.rit.iste500.dubai.RapidsCemeteryAPI.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import edu.rit.iste500.dubai.RapidsCemeteryAPI.model.User;
import edu.rit.iste500.dubai.RapidsCemeteryAPI.request.UserRequest;
import edu.rit.iste500.dubai.RapidsCemeteryAPI.service.PasswordService;
import edu.rit.iste500.dubai.RapidsCemeteryAPI.service.UserService;

/*
This controller facilitates the GET methods used to get users and log them into the admin panel
*/

@RestController
@RequestMapping(value = "/api/admin", produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminController {
	@Autowired
	private UserService userService;
	@Autowired
	private PasswordService passwordService;
	
	//POST method for fetchAllUsers, returns a JSON object that contains all users data
	@RequestMapping(value = "/fetchAllUsers", method = RequestMethod.POST, produces = { "application/json" })
	public List<User> fetchAllUsers(HttpServletRequest request, HttpServletResponse response) {

		List<User> users = new ArrayList<>();
		try {

			users = userService.fetchAllUsers();

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return users;
	}
	
	//POST method for saveUser, method logs user into admin panel
	@RequestMapping(value = "/saveUser", method = RequestMethod.POST, produces = { "application/json" })
	public User saveUser(@RequestBody UserRequest userRequest, HttpServletRequest request,
			HttpServletResponse response) {

		User user = null;
		try {

			user = userRequest.getUser();
			if (StringUtils.isEmpty(user.getPassword())) {
				passwordService.newPassword(user, true);
			}

			user = userService.save(user);
			user = userService.loadUserById(user.getId());

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return user;
	}

}
