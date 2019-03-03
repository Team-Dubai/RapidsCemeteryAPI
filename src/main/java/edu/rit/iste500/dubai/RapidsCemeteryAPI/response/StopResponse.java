package edu.rit.iste500.dubai.RapidsCemeteryAPI.response;

import java.util.List;

import edu.rit.iste500.dubai.RapidsCemeteryAPI.model.Stop;
import lombok.Data;

@Data
public class StopResponse {
	private List<Stop> stops;
	private Stop stop;

}
