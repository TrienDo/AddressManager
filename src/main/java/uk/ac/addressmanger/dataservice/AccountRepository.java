package uk.ac.addressmanger.dataservice;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

import uk.ac.addressmanger.model.Account;
 
@Transactional
public interface  AccountRepository extends CrudRepository<Account, Long> {	 
	  /**
	   * Return the user having the passed email or null if no user is found.
	   * 
	   * @param email the user email.
	   */
	  public Account findById(long id);
} 
