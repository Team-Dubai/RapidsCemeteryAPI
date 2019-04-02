package edu.rit.iste500.dubai.RapidsCemeteryAPI.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import edu.rit.iste500.dubai.RapidsCemeteryAPI.enums.CategoryEnum;
import edu.rit.iste500.dubai.RapidsCemeteryAPI.model.Item;

@Repository
public class ItemDao {
	@PersistenceContext
	private EntityManager entityManager;

	public List<Item> getAllItems() {

		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Item> criteria = builder.createQuery(Item.class);
		Root<Item> root = criteria.from(Item.class);
		criteria.select(root).orderBy(builder.asc(root.get("name")));

		List<Item> tempList = entityManager.createQuery(criteria).getResultList();
		return tempList;

	}

	public Item save(Item item) {

		Session session = entityManager.unwrap(Session.class);
		session.saveOrUpdate(item);
		session.flush();

		return item;
	}

	public Item getItemById(Long id) {

		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Item> criteria = builder.createQuery(Item.class);
		Root<Item> root = criteria.from(Item.class);
		criteria.select(root).where(builder.equal(root.get("id"), id));

		List<Item> tempList = entityManager.createQuery(criteria).getResultList();
		return tempList.size() > 0 ? tempList.get(0) : null;

	}

	public List<Item> getItemByCategory(CategoryEnum categoryEnum) {

		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Item> criteria = builder.createQuery(Item.class);
		Root<Item> root = criteria.from(Item.class);
		criteria.select(root).where(builder.equal(root.get("category"), categoryEnum))
				.orderBy(builder.asc(root.get("name")));

		List<Item> tempList = entityManager.createQuery(criteria).getResultList();
		return tempList;

	}

	public void removeItemById(long id) {


		Session session = entityManager.unwrap(Session.class);
		entityManager.createNativeQuery("ALTER TABLE rc_item_tags DISABLE TRIGGER ALL;").executeUpdate();
		entityManager.createNativeQuery("ALTER TABLE rc_stop_items DISABLE TRIGGER ALL;").executeUpdate();
		entityManager.createNativeQuery("ALTER TABLE rc_item DISABLE TRIGGER ALL;").executeUpdate();
		entityManager.flush();

		entityManager.createNativeQuery("delete from RC_ITEM_TAGS where item_id = :id").setParameter("id", id)
				.executeUpdate();
		entityManager.createNativeQuery("delete from RC_STOP_ITEMS where item_id = :id").setParameter("id", id)
				.executeUpdate();
		entityManager.flush();

		session.delete(getItemById(id));
		session.flush();
		entityManager.createNativeQuery("ALTER TABLE rc_item_tags ENABLE TRIGGER ALL;").executeUpdate();
		entityManager.createNativeQuery("ALTER TABLE rc_stop_items ENABLE TRIGGER ALL;").executeUpdate();
		entityManager.createNativeQuery("ALTER TABLE rc_item ENABLE TRIGGER ALL;").executeUpdate();

		entityManager.flush();


	}

}
