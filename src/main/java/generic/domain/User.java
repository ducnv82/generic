package generic.domain;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


/**
 * The persistent class for the app_user database table.
 * 
 */
@Entity
@Table(name="app_user")
@NamedQuery(name="User.findAll", query="SELECT u FROM User u")
public class User extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	private Boolean accountEnabled;
	private Boolean accountExpired;
	private Boolean accountLocked;
	private String address;
	private String city;
	private String country;
	private Boolean credentialsExpired;
	private String email;
	private String firstName;
	private String lastName;
	private String password;
	private String passwordHint;
	private String phoneNumber;
	private String postalCode;
	private String province;
	private String username;
	private Integer version;
	private String website;
	private Set<Role> roles;

	public User() {
	}


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	public Long getId() {
		return this.id;
	}


	@Column(name="account_enabled")
	public Boolean getAccountEnabled() {
		return this.accountEnabled;
	}

	public void setAccountEnabled(Boolean accountEnabled) {
		this.accountEnabled = accountEnabled;
	}


	@Column(name="account_expired", nullable=false)
	public Boolean getAccountExpired() {
		return this.accountExpired;
	}

	public void setAccountExpired(Boolean accountExpired) {
		this.accountExpired = accountExpired;
	}


	@Column(name="account_locked", nullable=false)
	public Boolean getAccountLocked() {
		return this.accountLocked;
	}

	public void setAccountLocked(Boolean accountLocked) {
		this.accountLocked = accountLocked;
	}


	@Column(length=150)
	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}


	@Column(length=50)
	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}


	@Column(length=100)
	public String getCountry() {
		return this.country;
	}

	public void setCountry(String country) {
		this.country = country;
	}


	@Column(name="credentials_expired", nullable=false)
	public Boolean getCredentialsExpired() {
		return this.credentialsExpired;
	}

	public void setCredentialsExpired(Boolean credentialsExpired) {
		this.credentialsExpired = credentialsExpired;
	}


	@Column(nullable=false, length=255)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}


	@Column(name="first_name", nullable=false, length=50)
	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	@Column(name="last_name", nullable=false, length=50)
	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	@Column(nullable=false, length=255)
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}


	@Column(name="password_hint", length=255)
	public String getPasswordHint() {
		return this.passwordHint;
	}

	public void setPasswordHint(String passwordHint) {
		this.passwordHint = passwordHint;
	}


	@Column(name="phone_number", length=255)
	public String getPhoneNumber() {
		return this.phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}


	@Column(name="postal_code", length=15)
	public String getPostalCode() {
		return this.postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}


	@Column(length=100)
	public String getProvince() {
		return this.province;
	}

	public void setProvince(String province) {
		this.province = province;
	}


	@Column(nullable=false, length=50)
	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}


	public Integer getVersion() {
		return this.version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}


	@Column(length=255)
	public String getWebsite() {
		return this.website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}


	//bi-directional many-to-many association to Role
	@ManyToMany
	@JoinTable(name = "user_role", joinColumns = { @JoinColumn(name = "user_id", nullable = false) }, inverseJoinColumns = { @JoinColumn(name = "role_id", nullable = false) })
	public Set<Role> getRoles() {
		return this.roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

}