package edu.rit.iste500.dubai.RapidsCemeteryAPI.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.rit.iste500.dubai.RapidsCemeteryAPI.dao.UserDao;
import edu.rit.iste500.dubai.RapidsCemeteryAPI.model.User;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserService implements UserDetailsService {

	@Autowired
	private UserDao userDao;

	@Transactional(readOnly = true)
	public User loadUserByUsername(String username) throws UsernameNotFoundException {
		log.debug("loadUserByUsername begin, username: {}", username);

		return userDao.getUserByUsername(username).get(0);
	}

	@Transactional(readOnly = true)
	public User loadUserById(Long userId) {
		log.debug("loadUserById begin, userId: {}", userId);

		User user = userDao.getUserById(userId);

		return user;
	}

	@Transactional
	public User save(final User user) throws Exception {

		return userDao.save(user);
	}

	@Transactional(readOnly = true)
	public List<User> fetchAllUsers() {
		return userDao.getAllUsers();
	}

	@Transactional(readOnly = true)
	public String fetchEmailByUsername(final String username) {
		return userDao.getEmailByUsername(username);
	}

}
