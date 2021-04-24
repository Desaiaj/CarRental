package ca.sheridancollege.carrental.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import ca.sheridancollege.carrental.beans.Role;
import ca.sheridancollege.carrental.beans.User;
import ca.sheridancollege.carrental.repositories.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		// Find the custom user based on their email
		User user = userRepository.findByEmail(username);
		// If the user doesn't exist, throw an exception
		if (user == null) {
			System.out.println("User not found:" + username);
			throw new UsernameNotFoundException("User " + username + " was not found in the database");
		}

		// Change the list of the user's roles into a list of GrantedAuthority
		List<GrantedAuthority> grantList = new ArrayList<GrantedAuthority>();
		for (Role role : user.getRoleList()) {
			grantList.add(new SimpleGrantedAuthority(role.getRolename()));
		}

		// Create a Spring Boot “User” contained in a UserDetailsbased on the
		// information above
		UserDetails userDetails = (UserDetails) new org.springframework.security.core.userdetails.User(user.getEmail(),
				user.getEncryptedPassword(), grantList);
		System.out.println(userDetails.toString());
		return userDetails;
	}

}
