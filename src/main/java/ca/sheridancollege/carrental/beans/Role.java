package ca.sheridancollege.carrental.beans;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
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
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Role {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NonNull
	private String rolename;

	@ManyToMany(cascade = CascadeType.ALL, mappedBy = "roleList")
	private List<User> userList = new ArrayList<User>();

}
