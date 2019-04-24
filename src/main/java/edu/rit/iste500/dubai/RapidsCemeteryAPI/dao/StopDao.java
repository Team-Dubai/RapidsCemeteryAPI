package edu.rit.iste500.dubai.RapidsCemeteryAPI.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import edu.rit.iste500.dubai.RapidsCemeteryAPI.model.Stop;

@Repository
public class StopDao {
	@PersistenceContext
	private EntityManager entityManager;

	public List<Stop> getAllStops() {

		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Stop> criteria = builder.createQuery(Stop.class);
		Root<Stop> root = criteria.from(Stop.class);
		criteria.orderBy(builder.asc(root.get("id")));

		List<Stop> tempList = entityManager.createQuery(criteria).getResultList();
		return tempList;
		// return session.createQuery(criteriaQuery).getResultList();

	}

	public Stop save(Stop stop) {

		Session session = entityManager.unwrap(Session.class);
		session.saveOrUpdate(stop);
		session.flush();

		return stop;
	}

	public Stop getStopById(Long id) {

		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Stop> criteria = builder.createQuery(Stop.class);
		Root<Stop> root = criteria.from(Stop.class);
		criteria.select(root).where(builder.equal(root.get("id"), id));

		List<Stop> tempList = entityManager.createQuery(criteria).getResultList();
		return tempList.size() > 0 ? tempList.get(0) : null;

	}

	public void removeStopById(long id) {
		Stop stop;

		Session session = entityManager.unwrap(Session.class);
		// session.createQuery("delete from Stop where id="+id);
		// session.createSQLQuery("delete from rc_stop where id=" + id);
		session.delete(getStopById(id));
		// stop = getStopById(id);
		// session.delete(stop);

		// This makes the pending delete to be done
		session.flush();

		// Query query = session.createQuery("delete Stop where Stop.id > :id");
		// query.setParameter("id", id);
		//
		// int result = query.executeUpdate();
		//
		// if (result > 0) {
		// System.out.println("Expensive products was removed");
		// }
	}

}
