package generic.domain;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


/**
 * The persistent class for the role database table.
 * 
 */
@Entity
@Table(name="role")
@NamedQuery(name="Role.findAll", query="SELECT r FROM Role r")
public class Role extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	private String description;
	private String name;
	private Set<User> users;

	public Role() {
	}

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	public Long getId() {
		return id;
	}

	@Column(length=64)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}


	@Column(length=20)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}


	//bi-directional many-to-many association to User
	@ManyToMany(mappedBy="roles", cascade = CascadeType.ALL)
	public Set<User> getUsers() {
		return this.users;
	}

	public void setUsers(Set<User> appUsers) {
		this.users = appUsers;
	}

}