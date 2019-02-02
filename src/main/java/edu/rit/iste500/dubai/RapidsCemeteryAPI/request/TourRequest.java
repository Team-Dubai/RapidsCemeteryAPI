package edu.rit.iste500.dubai.RapidsCemeteryAPI.request;

import edu.rit.iste500.dubai.RapidsCemeteryAPI.model.Tour;
import lombok.Data;

@Data
public class TourRequest {
	private long id;
	private Tour tour;
}
