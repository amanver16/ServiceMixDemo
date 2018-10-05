package org.example.webapp.controller;

import java.util.List;

import org.example.webapp.bean.RailwayStation;
import org.example.webapp.service.RailwayStationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/railwaystations")
public class RailwayStationController {

	@Autowired
	private RailwayStationServiceImpl railwayStationService;

	public RailwayStationServiceImpl getRailwayStationService() {
		return railwayStationService;
	}

	public void setRailwayStationService(RailwayStationServiceImpl railwayStationsService) {
		this.railwayStationService = railwayStationsService;
	}

	@RequestMapping("/railwaystationlist.json")
	public @ResponseBody List<RailwayStation> getRailwayStationList() {
		return railwayStationService.getAllRailwayStations();
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public @ResponseBody void addRailwayStation(@RequestBody RailwayStation railwayStation) {
		railwayStationService.addRailwayStation(railwayStation);
	}

	@RequestMapping(value = "/update", method = RequestMethod.PUT)
	public @ResponseBody void updateRailwayStation(@RequestBody RailwayStation railwayStation) {
		railwayStationService.updateRailwayStation(railwayStation);
	}

	@RequestMapping(value = "/remove/{id}", method = RequestMethod.DELETE)
	public @ResponseBody void removeRailwayStation(@PathVariable("id") Long id) {
		railwayStationService.deleteRailwayStationById(id);
	}

	@RequestMapping(value = "/removeAll", method = RequestMethod.DELETE)
	public @ResponseBody void removeAllRailwayStations() {
		railwayStationService.deleteAll();
	}

	@RequestMapping("/layout")
	public String getRailwayStationPartialPage(ModelMap modelMap) {
		return "railwaystations/layout";
	}
}
