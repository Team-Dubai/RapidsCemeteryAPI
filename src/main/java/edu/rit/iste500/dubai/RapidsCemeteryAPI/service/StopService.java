package edu.rit.iste500.dubai.RapidsCemeteryAPI.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.rit.iste500.dubai.RapidsCemeteryAPI.dao.StopDao;
import edu.rit.iste500.dubai.RapidsCemeteryAPI.model.Stop;

@Service
public class StopService {

	@Autowired
	private StopDao stopDao;

	@Transactional
	public Stop save(Stop stop) {
		return stopDao.save(stop);
	}

	@Transactional
	public List<Stop> getAllStops() {
		return stopDao.getAllStops();
	}

	@Transactional
	public Stop getStopById(Long id) {
		return stopDao.getStopById(id);
	}

	@Transactional
	public void removeStopById(Long id) {
		stopDao.removeStopById(id);
	}
}
