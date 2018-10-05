package org.example.webapp.controller;

import java.util.List;

import org.example.webapp.bean.Train;
import org.example.webapp.service.TrainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/trains")
public class TrainController {

	@Autowired
	private TrainService trainService;

	public TrainService getTrainService() {
		return trainService;
	}

	public void setTrainService(TrainService trainService) {
		this.trainService = trainService;
	}

	@RequestMapping("/trainslist.json")
	public @ResponseBody List<Train> getTrainList() {
		return trainService.getAllTrains();
	}

	@RequestMapping(value = "/addTrain", method = RequestMethod.POST)
	public @ResponseBody void addTrain(@RequestBody Train train) {
		trainService.addTrain(train);
	}

	@RequestMapping(value = "/updateTrain", method = RequestMethod.PUT)
	public @ResponseBody void updateTrain(@RequestBody Train train) {
		trainService.updateTrain(train);
	}

	@RequestMapping(value = "/removeTrain/{id}", method = RequestMethod.DELETE)
	public @ResponseBody void removeTrain(@PathVariable("id") Long id) {
		trainService.deleteTrainById(id);
	}

	@RequestMapping(value = "/removeAllTrains", method = RequestMethod.DELETE)
	public @ResponseBody void removeAllTrains() {
		trainService.deleteAll();
	}

	@RequestMapping("/layout")
	public String getTrainPartialPage(ModelMap modelMap) {
		return "trains/layout";
	}
}
