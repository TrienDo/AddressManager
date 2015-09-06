package uk.ac.addressmanger.dataservice;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import uk.ac.addressmanger.model.Address;
//http://docs.spring.io/spring-data/jpa/docs/1.8.0.RELEASE/reference/html/#jpa.named-parameters
@Transactional
public interface  AddressRepository extends CrudRepository<Address, Long> {
//	public interface  AddressRepository extends JpaRepository<Address, Long> {

	 // @Query("select address from Address addressu where address.user.id = ?1")
	  //List<Address> getAddressesForUser(long user_id);
	 
	  /**
	   * Return the user having the passed email or null if no user is found.
	   * 
	   * @param email the user email.
	   */
	  public Address findById(long id);
} 
