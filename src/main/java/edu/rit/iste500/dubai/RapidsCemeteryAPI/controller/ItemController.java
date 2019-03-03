package edu.rit.iste500.dubai.RapidsCemeteryAPI.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import edu.rit.iste500.dubai.RapidsCemeteryAPI.enums.ExceptionEnum;
import edu.rit.iste500.dubai.RapidsCemeteryAPI.exception.APIException;
import edu.rit.iste500.dubai.RapidsCemeteryAPI.model.Item;
import edu.rit.iste500.dubai.RapidsCemeteryAPI.request.ItemRequest;
import edu.rit.iste500.dubai.RapidsCemeteryAPI.response.ItemResponse;
import edu.rit.iste500.dubai.RapidsCemeteryAPI.service.ItemService;

@RestController
@RequestMapping(value = "/api/item", produces = MediaType.APPLICATION_JSON_VALUE)
public class ItemController {

	@Autowired
	private ItemService itemService;

	@CrossOrigin(origins = { "*" })
	@RequestMapping(value = "/getAllItems", method = RequestMethod.GET, produces = { "application/json" })
	public List<Item> getAllItems(HttpServletRequest request, HttpServletResponse response) {

		ItemResponse apiResponse = new ItemResponse();
		List<Item> items = null;

		try {
			items = itemService.getAllItems();

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return items;
	}

	@CrossOrigin(origins = { "*" })
	@RequestMapping(value = "/saveItem", method = RequestMethod.POST, produces = { "application/json" })
	public Item saveItem(@RequestBody ItemRequest itemRequest, HttpServletRequest request,
			HttpServletResponse response) {

		Item item = null;
		try {
			validateSaveItemRequest(itemRequest);
			item = itemService.save(itemRequest.getItem());

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return item;
	}

	@CrossOrigin(origins = { "*" })
	@RequestMapping(value = "/getItemById", method = RequestMethod.POST, produces = { "application/json" })
	public Item getItemById(@RequestBody ItemRequest itemRequest, HttpServletRequest request,
			HttpServletResponse response) {

		Item item = null;
		try {
			if (itemRequest.getId() > 0) {
				item = itemService.getItemById(itemRequest.getId());
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return item;
	}

	@CrossOrigin(origins = { "*" })
	@RequestMapping(value = "/getItemsByCategory", method = RequestMethod.POST, produces = { "application/json" })
	public List<Item> getItemsByCategory(@RequestBody ItemRequest itemRequest, HttpServletRequest request,
			HttpServletResponse response) {

		List<Item> items = null;

		try {
			if (itemRequest.getCategory() != null) {
				items = itemService.getItemByCategory(itemRequest.getCategory());
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return items;
	}

	@CrossOrigin(origins = { "*" })
	@RequestMapping(value = "/removeItemById", method = RequestMethod.POST, produces = { "application/json" })
	public boolean removeItemById(@RequestBody ItemRequest itemRequest, HttpServletRequest request,
			HttpServletResponse response) {

		try {
			if (itemRequest.getId() > 0) {
				itemService.removeItemById(itemRequest.getId());
				return true;

			}

		} catch (Exception ex) {
			ex.printStackTrace();
			return false;

		}
		return false;

	}

	private void validateSaveItemRequest(ItemRequest request) throws APIException {
		if (request.getItem() == null) {
			throw new APIException(ExceptionEnum.BAD_REQUEST, null);
		}
	}

}
