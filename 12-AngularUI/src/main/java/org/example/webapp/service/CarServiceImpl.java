package org.example.webapp.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service("carService")
public class CarServiceImpl implements CarService {
	private static List<String> carList = new ArrayList<String>();

	public List<String> getAllCars() {
		return carList;
	}

	public void addCar(String car) {
		carList.add(car);
		System.out.println("Car Size : " + carList.size());
	}

	public void deleteCar(String car) {
		if (carList.contains(car)) {
			carList.remove(car);
		}
	}

	public void deleteAll() {
		carList.clear();
	}
}
