package ca.sheridancollege.carrental.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ca.sheridancollege.carrental.beans.Car;
import ca.sheridancollege.carrental.beans.Customer;
import ca.sheridancollege.carrental.beans.Rental;

@Repository
public interface RentalRepository extends JpaRepository<Rental, Integer> {

	List<Rental> findByCustomer(Customer customer);

	List<Rental> findByCar(Car car);
}
