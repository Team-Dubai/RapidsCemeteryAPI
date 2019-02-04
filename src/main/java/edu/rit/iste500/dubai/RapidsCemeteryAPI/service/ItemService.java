package edu.rit.iste500.dubai.RapidsCemeteryAPI.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.rit.iste500.dubai.RapidsCemeteryAPI.dao.ItemDao;
import edu.rit.iste500.dubai.RapidsCemeteryAPI.model.Item;

@Service
public class ItemService {

	@Autowired
	private ItemDao itemDao;

	@Transactional
	public Item save(Item item) {
		return itemDao.save(item);
	}

	@Transactional
	public List<Item> getAllItems() {
		return itemDao.getAllItems();
	}

	@Transactional
	public Item getItemById(Long id) {
		return itemDao.getItemById(id);
	}

}
