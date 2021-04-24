package ca.sheridancollege.carrental.controllers;

import java.util.List;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ca.sheridancollege.carrental.beans.Car;
import ca.sheridancollege.carrental.beans.Customer;
import ca.sheridancollege.carrental.beans.Rental;
import ca.sheridancollege.carrental.repositories.CarRepository;
import ca.sheridancollege.carrental.repositories.CustomerRepository;
import ca.sheridancollege.carrental.repositories.RentalRepository;

@RestController
@RequestMapping("admin")
public class AdminController {

	private RentalRepository rentalRepository;
	private CarRepository carRepository;
	private CustomerRepository customerRepository;

	@GetMapping("running-rentals")
	public List<Rental> findRunningRentals() {
		return rentalRepository.findAll();
	}

	@PostMapping("create-car")
	public Car createCar(@RequestBody Car car) {
		if (carRepository.existsById(car.getId())) {
			throw new EntityExistsException("carAlreadyExists");
		}
		return carRepository.save(car);
	}

	@PostMapping("create-customer")
	public Customer createCustomer(@RequestBody Customer customer) {
		if (customerRepository.existsById(customer.getId())) {
			throw new EntityExistsException("customerAlreadyExists");
		}
		return customerRepository.save(customer);
	}

	@DeleteMapping("delete-car/{id}")
	public void deleteCar(@PathVariable Integer id) {
		Car car = carRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("carNotFound"));
		carRepository.delete(car);
	}

}
