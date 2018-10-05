package org.example.webapp.service;

import java.util.List;

public interface CarService {
	public List<String> getAllCars();

	public void addCar(String car);

	public void deleteCar(String car);

	public void deleteAll();
}
