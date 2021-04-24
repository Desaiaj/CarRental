package ca.sheridancollege.carrental.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import ca.sheridancollege.carrental.beans.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

	public Role findByRolename(String rolename);

}
