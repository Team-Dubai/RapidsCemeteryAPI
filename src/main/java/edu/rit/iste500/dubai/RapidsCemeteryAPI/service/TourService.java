package edu.rit.iste500.dubai.RapidsCemeteryAPI.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.rit.iste500.dubai.RapidsCemeteryAPI.dao.TourDao;
import edu.rit.iste500.dubai.RapidsCemeteryAPI.model.Tour;

@Service
public class TourService {

	@Autowired
	private TourDao tourDao;

	@Transactional
	public Tour save(Tour tour) {
		return tourDao.save(tour);
	}

	@Transactional
	public List<Tour> getAllTours() {
		return tourDao.getAllTours();
	}

	@Transactional
	public Tour getTourById(Long id) {
		return tourDao.getTourById(id);
	}

}
