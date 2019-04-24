package edu.rit.iste500.dubai.RapidsCemeteryAPI.request;

import edu.rit.iste500.dubai.RapidsCemeteryAPI.model.Stop;
import lombok.Data;

@Data
public class StopRequest {
	private long id;
	private Stop stop;
}
