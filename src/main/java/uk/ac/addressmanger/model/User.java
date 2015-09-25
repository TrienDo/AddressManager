package uk.ac.addressmanger.model;

 
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.fasterxml.jackson.annotation.JsonIgnore;



@Entity
@Table(name = "users")
public class User {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
 
    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    @OneToMany(mappedBy = "user")
    private Set<UserRole> roles;
  
    @OneToMany(mappedBy = "user")
    private List<Address> addresses;
    
    @OneToMany(mappedBy = "user")
    private List<Account> accounts = new ArrayList<Account>();

    public static User createUser(String username, String email, String password) {
        User user = new User();

        user.username = username;
        user.email = email;
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        user.password = passwordEncoder.encode(password);
        user.addresses = new ArrayList<Address>();
        
        if(user.roles == null) {
            user.roles = new HashSet<UserRole>();
        }

        //create a new user with basic user privileges
        user.roles.add(
                new UserRole(
                        RoleEnum.USER.toString(),
                        user
                ));

        return user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

     
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    //@XmlTransient
    @JsonIgnore
    public Set<UserRole> getRoles() {
        return roles;
    }

    public void setRoles(Set<UserRole> roles) {
        this.roles = roles;
    }

    //@XmlTransient
    @JsonIgnore
    public List<Address> getAddresses() {
		return addresses;
	}
    
	public void setAddresses(List<Address> addresses) {
		this.addresses = addresses;
	}

	@JsonIgnore
	public List<Account> getAccounts() {
		return accounts;
	}

	public void setAccounts(List<Account> accounts) {
		this.accounts = accounts;
	}	
	
}
