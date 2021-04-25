package ca.sheridancollege.carrental.controllers;

import java.util.List;
import java.util.Scanner;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import ca.sheridancollege.carrental.beans.Car;
import ca.sheridancollege.carrental.beans.Customer;
import ca.sheridancollege.carrental.beans.Rental;
import ca.sheridancollege.carrental.repositories.CarRepository;
import ca.sheridancollege.carrental.repositories.CustomerRepository;
import ca.sheridancollege.carrental.repositories.RentalRepository;
import lombok.AllArgsConstructor;

@Controller
@RequestMapping("admin")
@AllArgsConstructor
public class AdminController {

	private RentalRepository rentalRepository;
	private CarRepository carRepository;
	private CustomerRepository customerRepository;

	@GetMapping("/customers")
	public String customers(Model model, HttpServletRequest req) {

		var s = req.getRemoteUser();
		String fn = "";
		String ln = "";
		if (s != null && s != "") {
			Scanner sc = new Scanner(s);
			while (sc.hasNext()) {
				sc.useDelimiter(",");
				String s1 = sc.next();
				System.out.println(s1);
				if (s1.trim().startsWith("given_name=")) {
					fn = s1.replace("given_name=", "");
				}
				if (s1.trim().startsWith("family_name=")) {
					ln = s1.replace("family_name=", "");
				}
			}
		}
		if (fn == "" || ln == "")
			fn = req.getRemoteUser();
		model.addAttribute("username", fn + " " + ln);

		model.addAttribute("rentals", rentalRepository.findAll());
		model.addAttribute("customers", customerRepository.findAll());
		return "fragments/customers";
	}

	@GetMapping("/deleteCust/{id}")
	public String deleteCust(@PathVariable("id") Integer id, Model model) {
		var c = customerRepository.findById(id).get();
		if (c != null) {
			List<Car> cars = carRepository.findAll();
			rentalRepository.deleteAll(rentalRepository.findByCustomer(c));
			carRepository.deleteAll();
			carRepository.saveAll(cars);
		}
		model.addAttribute("rentals", rentalRepository.findAll());
		model.addAttribute("customers", customerRepository.findAll());
		return "redirect:/admin/customers";
	}

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

	@GetMapping("createCustomer")
	public String getCCustomer(Model model, HttpServletRequest req) {

		var s = req.getRemoteUser();
		String fn = "";
		String ln = "";
		if (s != null && s != "") {
			Scanner sc = new Scanner(s);
			while (sc.hasNext()) {
				sc.useDelimiter(",");
				String s1 = sc.next();
				System.out.println(s1);
				if (s1.trim().startsWith("given_name=")) {
					fn = s1.replace("given_name=", "");
				}
				if (s1.trim().startsWith("family_name=")) {
					ln = s1.replace("family_name=", "");
				}
			}
		}
		if (fn == "" || ln == "")
			fn = req.getRemoteUser();
		model.addAttribute("username", fn + " " + ln);
		model.addAttribute("customer", new Customer());
		return "fragments/createcustomers";
	}

	@PostMapping("createcustomer")
	public String createCustomer(@ModelAttribute Customer customer) {
		if (customerRepository.findByFirstNameAndLastName(customer.getFirstName(), customer.getLastName()) != null) {
			throw new EntityExistsException("customerAlreadyExists");
		}
		customerRepository.save(customer);
		return "redirect:/admin/customers";
	}

	@GetMapping("createCar")
	public String createCar(Model model, HttpServletRequest req) {

		var s = req.getRemoteUser();
		String fn = "";
		String ln = "";
		if (s != null && s != "") {
			Scanner sc = new Scanner(s);
			while (sc.hasNext()) {
				sc.useDelimiter(",");
				String s1 = sc.next();
				System.out.println(s1);
				if (s1.trim().startsWith("given_name=")) {
					fn = s1.replace("given_name=", "");
				}
				if (s1.trim().startsWith("family_name=")) {
					ln = s1.replace("family_name=", "");
				}
			}
		}
		if (fn == "" || ln == "")
			fn = req.getRemoteUser();
		model.addAttribute("username", fn + " " + ln);
		model.addAttribute("car", new Car());
		model.addAttribute("cars", carRepository.findAll());
		return "fragments/cars";
	}

	@PostMapping("createcar")
	public String createcar(@ModelAttribute Car car) {
		carRepository.save(car);
		return "redirect:/admin/createCar";
	}

	@GetMapping("delete-car/{id}")
	public String deleteCar(@PathVariable Integer id) {
		Car car = carRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("carNotFound"));
		carRepository.delete(car);
		return "redirect:/admin/createCar";
	}

	@GetMapping("delete-rental/{id}")
	public String deleterental(@PathVariable Integer id) {
		Rental r = rentalRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("carNotFound"));
		rentalRepository.delete(r);
		return "redirect:/admin/customers";
	}

}
