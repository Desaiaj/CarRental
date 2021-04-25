package ca.sheridancollege.carrental.beans;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Entity
@Builder
@NoArgsConstructor
public class Rental {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private LocalDate rentalDate;
	private LocalDate returnDate;
	private Integer km;
	private Integer carid;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinTable(name = "Customer_Rental", joinColumns = @JoinColumn(name = "RENTAL_ID"), inverseJoinColumns = @JoinColumn(name = "cust_id"))
	private Customer customer = new Customer();

	@OneToOne(cascade = CascadeType.ALL)
	@JoinTable(name = "Car_Rental", joinColumns = @JoinColumn(name = "car_id"), inverseJoinColumns = @JoinColumn(name = "rental_id"))
	private Car car = new Car();
}
