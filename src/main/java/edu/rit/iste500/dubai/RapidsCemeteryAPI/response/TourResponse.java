package edu.rit.iste500.dubai.RapidsCemeteryAPI.response;

import java.util.List;

import edu.rit.iste500.dubai.RapidsCemeteryAPI.model.Tour;
import lombok.Data;

@Data
public class TourResponse {
	private List<Tour> tours;
	private Tour tour;

}
