package ca.sheridancollege.carrental.beans;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
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
	private Integer custid;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinTable(name = "Customer_Rental", joinColumns = @JoinColumn(name = "id"), inverseJoinColumns = @JoinColumn(name = "id"))
	private Customer customer = new Customer();

	@OneToOne(cascade = CascadeType.ALL)
	@JoinTable(name = "Car_Rental", joinColumns = @JoinColumn(name = "id"), inverseJoinColumns = @JoinColumn(name = "id"))
	private Car car = new Car();
}
