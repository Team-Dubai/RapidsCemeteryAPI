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
import edu.rit.iste500.dubai.RapidsCemeteryAPI.model.Stop;
import edu.rit.iste500.dubai.RapidsCemeteryAPI.request.StopRequest;
import edu.rit.iste500.dubai.RapidsCemeteryAPI.response.StopResponse;
import edu.rit.iste500.dubai.RapidsCemeteryAPI.service.StopService;

@RestController
@RequestMapping(value = "/api/stop", produces = MediaType.APPLICATION_JSON_VALUE)
public class StopController {

	@Autowired
	private StopService stopService;
	
	//getAllStops GET method that will return a JSON object that contains all stop data
	@CrossOrigin(origins = { "*" })
	@RequestMapping(value = "/getAllStops", method = RequestMethod.GET, produces = { "application/json" })
	public List<Stop> getAllStops(HttpServletRequest request, HttpServletResponse response) {

		StopResponse apiResponse = new StopResponse();
		List<Stop> stops = null;

		try {
			stops = stopService.getAllStops();

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return stops;
	}
	
	//saveStop POST method that will save a new stop entered by the admin
	@CrossOrigin(origins = { "*" })
	@RequestMapping(value = "/saveStop", method = RequestMethod.POST, produces = { "application/json" })
	public Stop saveStop(@RequestBody StopRequest stopRequest, HttpServletRequest request,
			HttpServletResponse response) {

		Stop stop = null;
		try {
			validateSaveStopRequest(stopRequest);
			stop = stopService.save(stopRequest.getStop());

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return stop;
	}
	
	//getStopsById POST method that will return a specific stop based off the entered ID
	@CrossOrigin(origins = { "*" })
	@RequestMapping(value = "/getStopById", method = RequestMethod.POST, produces = { "application/json" })
	public Stop getStopById(@RequestBody StopRequest stopRequest, HttpServletRequest request,
			HttpServletResponse response) {

		Stop stop = null;
		try {
			if (stopRequest.getId() > 0) {
				stop = stopService.getStopById(stopRequest.getId());
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return stop;
	}
	
	//removeStopById POST method that will delete a specific stop
	@CrossOrigin(origins = { "*" })
	@RequestMapping(value = "/removeStopById", method = RequestMethod.POST, produces = { "application/json" })
	public boolean removeStopById(@RequestBody StopRequest stopRequest, HttpServletRequest request,
			HttpServletResponse response) {

		try {
			if (stopRequest.getId() > 0) {
				stopService.removeStopById(stopRequest.getId());
				return true;

			}

		} catch (Exception ex) {
			ex.printStackTrace();
			return false;

		}
		return false;

	}

	private void validateSaveStopRequest(StopRequest request) throws APIException {
		if (request.getStop() == null) {
			throw new APIException(ExceptionEnum.BAD_REQUEST, null);
		}
	}

}
