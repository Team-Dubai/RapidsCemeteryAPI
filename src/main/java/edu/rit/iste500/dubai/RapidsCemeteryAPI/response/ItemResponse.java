package edu.rit.iste500.dubai.RapidsCemeteryAPI.response;

import java.util.List;

import edu.rit.iste500.dubai.RapidsCemeteryAPI.model.Item;
import lombok.Data;

@Data
public class ItemResponse {
	private List<Item> items;
	private Item item;

}
