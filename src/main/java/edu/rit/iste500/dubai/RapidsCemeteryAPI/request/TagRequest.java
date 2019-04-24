package edu.rit.iste500.dubai.RapidsCemeteryAPI.request;

import edu.rit.iste500.dubai.RapidsCemeteryAPI.model.Tag;
import lombok.Data;

@Data
public class TagRequest {
	private long id;
	private Tag tag;
}
