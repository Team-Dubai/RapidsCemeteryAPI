package edu.rit.iste500.dubai.RapidsCemeteryAPI.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.rit.iste500.dubai.RapidsCemeteryAPI.dao.TagDao;
import edu.rit.iste500.dubai.RapidsCemeteryAPI.model.Tag;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TagService {

	@Autowired
	private TagDao tagDao;

	@Transactional(readOnly = true)
	public Tag loadTagByTagname(String tagname) {
		log.debug("loadTagByTagname begin, tagname: {}", tagname);

		return tagDao.getTagByTagname(tagname).get(0);
	}

	@Transactional(readOnly = true)
	public Tag loadTagById(Long tagId) {
		log.debug("loadTagById begin, tagId: {}", tagId);

		Tag tag = tagDao.getTagById(tagId);

		return tag;
	}

	@Transactional
	public Tag save(final Tag tag) throws Exception {

		return tagDao.save(tag);
	}

	@Transactional(readOnly = true)
	public List<Tag> fetchAllTags() {
		return tagDao.getAllTags();
	}

	@Transactional
	public void removeTagById(Long id) {
		tagDao.removeTagById(id);
	}

}
