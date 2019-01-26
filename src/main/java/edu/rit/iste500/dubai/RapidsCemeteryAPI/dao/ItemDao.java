package edu.rit.iste500.dubai.RapidsCemeteryAPI.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaQuery;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import edu.rit.iste500.dubai.RapidsCemeteryAPI.model.Item;

@Repository
public class ItemDao {
	@PersistenceContext
	private EntityManager entityManager;

	public List<Item> getAllItems() {



		Session session = entityManager.unwrap(Session.class);
		CriteriaQuery<Item> criteriaQuery = session.getCriteriaBuilder().createQuery(Item.class);
		criteriaQuery.from(Item.class);



		return session.createQuery(criteriaQuery).getResultList();

	}

	public Item save(Item item) {

		Session session = entityManager.unwrap(Session.class);
		session.saveOrUpdate(item);
		session.flush();

		return item;
	}

}
