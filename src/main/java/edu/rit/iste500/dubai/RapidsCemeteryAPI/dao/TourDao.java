package edu.rit.iste500.dubai.RapidsCemeteryAPI.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import edu.rit.iste500.dubai.RapidsCemeteryAPI.model.Tour;

@Repository
public class TourDao {
	@PersistenceContext
	private EntityManager entityManager;

	public List<Tour> getAllTours() {

		Session session = entityManager.unwrap(Session.class);
		CriteriaQuery<Tour> criteriaQuery = session.getCriteriaBuilder().createQuery(Tour.class);
		criteriaQuery.from(Tour.class);

		return session.createQuery(criteriaQuery).getResultList();

	}

	public Tour save(Tour tour) {

		Session session = entityManager.unwrap(Session.class);
		session.saveOrUpdate(tour);
		session.flush();

		return tour;
	}

	public Tour getTourById(Long id) {

		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Tour> criteria = builder.createQuery(Tour.class);
		Root<Tour> root = criteria.from(Tour.class);
		criteria.select(root).where(builder.equal(root.get("id"), id));

		List<Tour> tempList = entityManager.createQuery(criteria).getResultList();
		return tempList.size() > 0 ? tempList.get(0) : null;

	}

}
