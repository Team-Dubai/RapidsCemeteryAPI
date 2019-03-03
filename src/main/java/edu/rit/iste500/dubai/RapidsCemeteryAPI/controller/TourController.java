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
import edu.rit.iste500.dubai.RapidsCemeteryAPI.model.Tour;
import edu.rit.iste500.dubai.RapidsCemeteryAPI.request.TourRequest;
import edu.rit.iste500.dubai.RapidsCemeteryAPI.response.TourResponse;
import edu.rit.iste500.dubai.RapidsCemeteryAPI.service.TourService;

@RestController
@RequestMapping(value = "/api/tour", produces = MediaType.APPLICATION_JSON_VALUE)
public class TourController {

	@Autowired
	private TourService tourService;

	@RequestMapping(value = "/getAllTours", method = RequestMethod.GET, produces = { "application/json" })
	public List<Tour> getAllTours(HttpServletRequest request, HttpServletResponse response) {

		TourResponse apiResponse = new TourResponse();
		List<Tour> tours = null;
		try {
			tours = tourService.getAllTours();

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		for (Tour tour : tours) {
			if (tour.getStops().contains(null)) {
				tour.getStops().remove(null);
			}
		}

		return tours;
	}

	@RequestMapping(value = "/saveTour", method = RequestMethod.POST, produces = { "application/json" })
	public TourResponse saveTour(@RequestBody TourRequest tourRequest, HttpServletRequest request,
			HttpServletResponse response) {

		TourResponse apiResponse = new TourResponse();

		try {
			validateSaveTourRequest(tourRequest);
			apiResponse.setTour(tourService.save(tourRequest.getTour()));

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return apiResponse;
	}

	@RequestMapping(value = "/getTour", method = RequestMethod.POST, produces = { "application/json" })
	public Tour getTour(@RequestBody TourRequest tourRequest, HttpServletRequest request,
			HttpServletResponse response) {

		TourResponse apiResponse = new TourResponse();
		Tour tour = null;

		try {
			tour = tourService.getTourById(tourRequest.getId());

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		if (tour.getStops().contains(null)) {
			tour.getStops().remove(null);
		}
		return tour;
	}

	private void validateSaveTourRequest(TourRequest request) throws APIException {
		if (request.getTour() == null) {
			throw new APIException(ExceptionEnum.BAD_REQUEST, null);
		}
	}

}
