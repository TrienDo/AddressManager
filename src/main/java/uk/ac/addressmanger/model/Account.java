package uk.ac.addressmanger.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Account {
	@Id 
	@GeneratedValue
	private long id;
	private String name;
	private String website;
	private String description;
	 
	
	@ManyToOne	
	private User user;
	
	public Account()
	{
		
	}
	
	public Account(String name, String website, String description) {		
		this.name = name;
		this.website = website;
		this.description = description;
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	//@XmlTransient
	@JsonIgnore
	public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
