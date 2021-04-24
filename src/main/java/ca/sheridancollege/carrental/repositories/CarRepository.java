package ca.sheridancollege.carrental.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ca.sheridancollege.carrental.beans.Car;

@Repository
public interface CarRepository extends JpaRepository<Car, Integer> {
}
