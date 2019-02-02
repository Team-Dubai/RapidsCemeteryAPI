package edu.rit.iste500.dubai.RapidsCemeteryAPI.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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

	@RequestMapping(value = "/getAllItems", method = RequestMethod.GET, produces = { "application/json" })
	public ItemResponse getAllItems(HttpServletRequest request, HttpServletResponse response) {

		ItemResponse apiResponse = new ItemResponse();

		try {
			List<Item> items = itemService.getAllItems();
			apiResponse.setItems(items);

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return apiResponse;
	}

	@RequestMapping(value = "/saveItem", method = RequestMethod.POST, produces = { "application/json" })
	public ItemResponse saveItem(@RequestBody ItemRequest itemRequest, HttpServletRequest request,
			HttpServletResponse response) {

		ItemResponse apiResponse = new ItemResponse();

		try {
			validateSaveItemRequest(itemRequest);
			apiResponse.setItem(itemService.save(itemRequest.getItem()));

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return apiResponse;
	}

	@RequestMapping(value = "/getItem", method = RequestMethod.POST, produces = { "application/json" })
	public ItemResponse getItem(@RequestBody ItemRequest itemRequest, HttpServletRequest request,
			HttpServletResponse response) {

		ItemResponse apiResponse = new ItemResponse();

		try {
			apiResponse.setItem(itemService.getItemById(itemRequest.getId()));

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return apiResponse;
	}

	private void validateSaveItemRequest(ItemRequest request) throws APIException {
		if (request.getItem() == null) {
			throw new APIException(ExceptionEnum.BAD_REQUEST, null);
		}
	}

}
