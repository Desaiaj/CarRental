package ca.sheridancollege.carrental.controllers;

import java.time.LocalDate;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
	public String showCreateRentalForm(Model model, @ModelAttribute("rental") Rental rental) {
		rental.setRentalDate(LocalDate.now());
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
	public ModelAndView processCreateRentalForm(@ModelAttribute("rental") Rental rental) {
		ModelAndView createRentalForm = new ModelAndView("fragments/create-rental");
		createRentalForm.addObject("customers", customerRepository.findAll());
		rental.setRentalDate(LocalDate.now());
		rental.setReturnDate(rental.getReturnDate());
		rentalRepository.save(rental);
		return new ModelAndView("redirect:/employee/running-rentals");
	}

	@GetMapping("/running-rentals")
	public String showRunningRentalsForm(Model model) {
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
