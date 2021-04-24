package ca.sheridancollege.carrental.beans;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * @author bhatt
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
@Entity
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NonNull
	private String email;
	@NonNull
	private String encryptedPassword;
	@NonNull
	private Boolean enabled;

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<Role> roleList = new ArrayList<Role>();

}
