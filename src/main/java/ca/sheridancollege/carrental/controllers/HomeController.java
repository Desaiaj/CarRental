package ca.sheridancollege.carrental.controllers;

import java.util.Scanner;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ca.sheridancollege.carrental.beans.Customer;
import ca.sheridancollege.carrental.beans.User;
import ca.sheridancollege.carrental.repositories.CarRepository;
import ca.sheridancollege.carrental.repositories.CustomerRepository;
import ca.sheridancollege.carrental.repositories.RoleRepository;
import ca.sheridancollege.carrental.repositories.UserRepository;
import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class HomeController {

	private UserRepository userRepository;
	private RoleRepository roleRepository;
	private CarRepository carRepository;
	private CustomerRepository customerRepository;

	private String encodePassword(String password) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder.encode(password);
	}

	@GetMapping
	public String index(Model model, HttpServletRequest req) {

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
		model.addAttribute("cars", carRepository.findAll());
		model.addAttribute("username", fn + " " + ln);
		return "index";
	}

	@GetMapping("/secure/check")
	public String check() {
		return "/secure/check";
	}

	@GetMapping("/loginSuccess")
	public String loginSuccess(HttpServletRequest req, Model model) {
		var s = req.getRemoteUser();
		Scanner sc = new Scanner(s);
		sc.useDelimiter(",");
		String fn = "";
		String ln = "";
		while (sc.hasNext()) {
			String s1 = sc.next();
			System.out.println(s1);
			if (s1.trim().startsWith("given_name=")) {
				fn = s1.replace("given_name=", "");
			}
			if (s1.trim().startsWith("family_name=")) {
				ln = s1.replace("family_name=", "");
			}
		}

		model.addAttribute("cars", carRepository.findAll());
		model.addAttribute("username", fn + " " + ln);
		var c = new Customer();
		c.setFirstName(fn);
		c.setLastName(ln);
		customerRepository.save(c);
		return "index";
	}

	@GetMapping("/secure")
	public String secureIndex() {
		return "/secure/index";
	}

	@GetMapping("/login")
	public String login() {
		return "login";
	}

	@GetMapping("/permission-denied")
	public String permissionDenied() {
		return "/error/permission-denied";
	}

	@GetMapping("/register")
	public String goRegistration() {
		return "register";
	}

	@PostMapping("/register")
	public String doRegistration(@RequestParam String email, @RequestParam String password) {
		User user = new User(email, encodePassword(password), true);
		user.getRoleList().add(roleRepository.findByRolename("ROLE_USER"));

		userRepository.save(user);

		return "redirect:/login";
	}
}
