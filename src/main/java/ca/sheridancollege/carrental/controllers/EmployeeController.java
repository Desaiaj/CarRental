package ca.sheridancollege.carrental.controllers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

import javax.servlet.http.HttpServletRequest;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ca.sheridancollege.carrental.beans.Car;
import ca.sheridancollege.carrental.beans.Customer;
import ca.sheridancollege.carrental.beans.Rental;
import ca.sheridancollege.carrental.repositories.CarRepository;
import ca.sheridancollege.carrental.repositories.CustomerRepository;
import ca.sheridancollege.carrental.repositories.RentalRepository;
import lombok.AllArgsConstructor;

@Controller
@RequestMapping("/employee")
@AllArgsConstructor
public class EmployeeController {
	private RentalRepository rentalRepository;
	private CarRepository carRepository;
	private CustomerRepository customerRepository;

	@GetMapping("/create-rental")
	public String showCreateRentalForm(Model model, @ModelAttribute("rental") Rental rental, HttpServletRequest req) {

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
		rental.setReturnDate(LocalDate.now());
		model.addAttribute("rental", rental);
		model.addAttribute("cars", carRepository.findAll());
		model.addAttribute("customers", customerRepository.findAll());

		return "fragments/create-rental";
	}

	@PostMapping("/create-rental/refresh")
	public String refreshCreateRentalForm(@ModelAttribute("rental") Rental rental,
			RedirectAttributes redirectAttributes) {
		redirectAttributes.addFlashAttribute("rental", rental);
		return "redirect:/employee/create-rental";
	}

	@PostMapping("/create-rental/process")
	public ModelAndView processCreateRentalForm(@RequestParam Car car, @RequestParam Customer customer,
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate returnDate) {
		ModelAndView createRentalForm = new ModelAndView("fragments/create-rental");
		createRentalForm.addObject("customers", customerRepository.findAll());

		var rental = new Rental();
		rental.setCar(car);
		rental.setCarid(car.getId());
		rental.setCustomer(customer);
		rental.setRentalDate(LocalDate.now());
		rental.setReturnDate(returnDate);
		rentalRepository.save(rental);
		return new ModelAndView("redirect:/employee/running-rentals");
	}

	@GetMapping("/running-rentals")
	public String showRunningRentalsForm(Model model, HttpServletRequest req) {

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
		return "fragments/running-rentals";
	}

	@GetMapping("/finish-rental/{id}")
	public ModelAndView showFinishForm(@PathVariable("id") Integer id) {
		boolean opt = rentalRepository.existsById(id);
		if (opt) {
			return new ModelAndView("redirect:/employee/running-rentals").addObject("error", "rentalNotFound");
		}

		return new ModelAndView("fragments/finish-rental");
	}

	@PostMapping("/finish-rental/{id}")
	public ModelAndView processFinishForm(@PathVariable("id") Integer id) {
		ModelAndView redirectRunningRentals = new ModelAndView("redirect:/employee/running-rentals");
		ModelAndView finishRentalForm = new ModelAndView("/fragments/finish-rental");

		Rental rental = new Rental();
		rental.setKm(2);

		rentalRepository.save(rental);
		return redirectRunningRentals.addObject("success", "finishRentalSuccess");
	}
}
