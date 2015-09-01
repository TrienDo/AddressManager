package uk.ac.addressmanger.dataservice;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

import uk.ac.addressmanger.model.Address;

@Transactional
public interface  AddressDao extends CrudRepository<Address, Long> {

	  /**
	   * Return the user having the passed email or null if no user is found.
	   * 
	   * @param email the user email.
	   */
	  public Address findById(int id);

} // class UserDao
