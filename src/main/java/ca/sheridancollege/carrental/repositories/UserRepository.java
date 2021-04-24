package ca.sheridancollege.carrental.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import ca.sheridancollege.carrental.beans.User;

public interface UserRepository extends JpaRepository<User, Long> {

	public User findByEmail(String email);

}
