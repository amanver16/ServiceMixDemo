package org.example.webapp.service;

import java.util.List;

import org.example.webapp.bean.Train;

public interface TrainService {
	public List<Train> getAllTrains();

	public Train getTrainById(Long id);

	public void addTrain(Train train);

	public void deleteTrainById(Long id);

	public void deleteAll();

	public void updateTrain(Train train);
}
