package edu.rit.iste500.dubai.RapidsCemeteryAPI.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import edu.rit.iste500.dubai.RapidsCemeteryAPI.model.Tag;

@Repository
public class TagDao {
	@PersistenceContext
	private EntityManager entityManager;

	public List<Tag> getAllTags() {

		Session session = entityManager.unwrap(Session.class);
		CriteriaQuery<Tag> criteriaQuery = session.getCriteriaBuilder().createQuery(Tag.class);
		criteriaQuery.from(Tag.class);

		return session.createQuery(criteriaQuery).getResultList();

	}

	public Tag save(Tag tag) {

		Session session = entityManager.unwrap(Session.class);
		session.saveOrUpdate(tag);
		session.flush();

		return tag;
	}

	public Tag getTagById(Long id) {

		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Tag> criteria = builder.createQuery(Tag.class);
		Root<Tag> root = criteria.from(Tag.class);
		criteria.select(root).where(builder.equal(root.get("id"), id));

		List<Tag> tempList = entityManager.createQuery(criteria).getResultList();
		return tempList.size() > 0 ? tempList.get(0) : null;

	}

	public List<Tag> getTagByTagname(String tagname) {

		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Tag> criteria = builder.createQuery(Tag.class);
		Root<Tag> root = criteria.from(Tag.class);
		criteria.select(root).where(builder.equal(root.get("tagname"), tagname));

		List<Tag> tempList = entityManager.createQuery(criteria).getResultList();
		return tempList;

	}

	public void removeTagById(long id) {

		Session session = entityManager.unwrap(Session.class);
		// entityManager.createNativeQuery("ALTER TABLE rc_item_tags DISABLE TRIGGER
		// ALL;").executeUpdate();
		// entityManager.createNativeQuery("ALTER TABLE rc_tag DISABLE TRIGGER
		// ALL;").executeUpdate();
		// entityManager.flush();
		entityManager.createNativeQuery("delete from RC_ITEM_TAGS where tag_id = :id").setParameter("id", id)
				.executeUpdate();
		entityManager.flush();
		session.delete(getTagById(id));
		session.flush();
		// entityManager.createNativeQuery("ALTER TABLE rc_item_tags ENABLE TRIGGER
		// ALL;").executeUpdate();
		// entityManager.createNativeQuery("ALTER TABLE rc_tag ENABLE TRIGGER
		// ALL;").executeUpdate();
		// entityManager.flush();

	}

}
