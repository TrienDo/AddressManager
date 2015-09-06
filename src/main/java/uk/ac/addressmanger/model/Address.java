package uk.ac.addressmanger.model;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlTransient;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Address {
	@Id 
	@GeneratedValue
	private long id;
	private String number;
	private String street;
	private String city;
	private String postcode;
	private String country; 
	
	@ManyToOne	
	private User user;
	
	public Address()
	{
		
	}
	
	public Address(String number, String street, String city, String postcode, String country) {		
		this.number = number;
		this.street = street;
		this.city = city;
		this.postcode = postcode;
		this.country = country;
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getPostcode() {
		return postcode;
	}
	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
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
