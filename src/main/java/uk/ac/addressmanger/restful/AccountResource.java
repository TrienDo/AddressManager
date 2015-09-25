package uk.ac.addressmanger.restful;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import uk.ac.addressmanger.dataservice.AccountRepository;
import uk.ac.addressmanger.dataservice.UserRepository;
import uk.ac.addressmanger.model.Account;
import uk.ac.addressmanger.model.User;

@RestController
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequestMapping("/users/{userId}/accounts")
public class AccountResource {
 	@Autowired
	private UserRepository userDao;
 	@Autowired
	private AccountRepository accountDao;
		
	
	@RequestMapping(method = RequestMethod.GET)
   	public List<Account> getAccounts(@PathVariable("userId") long id) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userDao.findByUsername(auth.getName());
		return user.getAccounts();
   	}
	
	@RequestMapping(method = RequestMethod.POST)   
   	public List<Account> addAccount(@PathVariable("userId") long id, @RequestBody final Account account) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userDao.findByUsername(auth.getName());
		user.getAccounts().add(account);
		account.setUser(user);
		accountDao.save(account);
		userDao.save(user);
    	return user.getAccounts();
   	}
    
	@RequestMapping(value ="/{accountId}", method = RequestMethod.PUT)//Order of params is important: PathVariable must stand before @RequestBody
   	public List<Account> updateAccount(@PathVariable("userId") long userId, @PathVariable("accountId") long accountId, @RequestBody final Account account) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userDao.findByUsername(auth.getName());
		account.setId(accountId);
		account.setUser(user);
		accountDao.save(account);
    	return user.getAccounts();
   	} 
	
	@RequestMapping(value ="/{accountId}", method = RequestMethod.DELETE)
   	public List<Account> deleteAccount(@PathVariable("userId") long userId, @PathVariable("accountId") long addressId) {    	 
		accountDao.delete(addressId);
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userDao.findByUsername(auth.getName());
		return user.getAccounts();
   	} 
	
	@RequestMapping(value ="/{accountId}", method = RequestMethod.GET)//User PathVar rather than PathParam
   	public Account getOneAccount(@PathVariable("accountId") long id) {
    	return (Account) accountDao.findById(id);
   	}
}