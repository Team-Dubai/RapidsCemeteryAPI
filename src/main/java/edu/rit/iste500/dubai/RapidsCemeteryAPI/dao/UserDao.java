package edu.rit.iste500.dubai.RapidsCemeteryAPI.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import edu.rit.iste500.dubai.RapidsCemeteryAPI.model.User;

@Repository
public class UserDao {
	@PersistenceContext
	private EntityManager entityManager;

	public List<User> getAllUsers() {

		Session session = entityManager.unwrap(Session.class);
		CriteriaQuery<User> criteriaQuery = session.getCriteriaBuilder().createQuery(User.class);
		criteriaQuery.from(User.class);

		return session.createQuery(criteriaQuery).getResultList();

	}

	public User save(User user) {

		Session session = entityManager.unwrap(Session.class);
		session.saveOrUpdate(user);
		session.flush();

		return user;
	}

	public User getUserById(Long id) {

		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<User> criteria = builder.createQuery(User.class);
		Root<User> root = criteria.from(User.class);
		criteria.select(root).where(builder.equal(root.get("id"), id));

		List<User> tempList = entityManager.createQuery(criteria).getResultList();
		return tempList.size() > 0 ? tempList.get(0) : null;

	}

	public List<User> getUserByUsername(String username) {

		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<User> criteria = builder.createQuery(User.class);
		Root<User> root = criteria.from(User.class);
		criteria.select(root).where(builder.equal(root.get("username"), username));

		List<User> tempList = entityManager.createQuery(criteria).getResultList();
		return tempList;

	}

	public String getEmailByUsername(String username) {

		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<User> criteria = builder.createQuery(User.class);
		Root<User> root = criteria.from(User.class);
		criteria.select(root).where(builder.equal(root.get("username"), username));

		List<User> tempList = entityManager.createQuery(criteria).getResultList();
		return tempList.get(0).getEmail();

	}

}
