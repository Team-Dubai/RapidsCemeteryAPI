package edu.rit.iste500.dubai.RapidsCemeteryAPI.request;

import edu.rit.iste500.dubai.RapidsCemeteryAPI.model.Item;
import lombok.Data;

@Data
public class ItemRequest {
	private long id;
	private Item item;
}
