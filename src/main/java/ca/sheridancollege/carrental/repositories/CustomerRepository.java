package ca.sheridancollege.carrental.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ca.sheridancollege.carrental.beans.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
}
