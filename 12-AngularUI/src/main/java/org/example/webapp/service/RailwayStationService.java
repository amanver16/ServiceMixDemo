package org.example.webapp.service;

import java.util.List;

import org.example.webapp.bean.RailwayStation;

public interface RailwayStationService {

	public List<RailwayStation> getAllRailwayStations();

	public RailwayStation getRailwayStationById(Long id);

	public void addRailwayStation(RailwayStation RailwayStation);

	public void deleteRailwayStationById(Long id);

	public void deleteAll();

	public void updateRailwayStation(RailwayStation RailwayStation);
}
