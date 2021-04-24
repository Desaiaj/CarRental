package ca.sheridancollege.carrental.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ca.sheridancollege.carrental.beans.Car;
import ca.sheridancollege.carrental.beans.Rental;

@Repository
public interface RentalRepository extends JpaRepository<Rental, Integer> {

	// @Query("SELECT r FROM Rental r WHERE r.km IS NULL AND r.returnDate IS NULL
	// AND r.returnStation IS NULL")
	// List<Rental> findRunningRentals();

	List<Rental> findByCar(Car car);
}
