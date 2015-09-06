package uk.ac.addressmanger.restful;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import uk.ac.addressmanger.dataservice.AddressRepository;
import uk.ac.addressmanger.dataservice.UserRepository;
import uk.ac.addressmanger.model.Address;
import uk.ac.addressmanger.model.User;

@RestController
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
//@RequestMapping("/addresses")
//@RequestMapping("/")//sub-resource of user
@RequestMapping("/users/{userId}/addresses")
public class AddressResource {
 	@Autowired
	private UserRepository userDao;
 	@Autowired
	private AddressRepository addressDao;
		
	
	@RequestMapping(method = RequestMethod.GET)
   	public List<Address> getAddresses(@PathVariable("userId") long id) {
    	return userDao.findById(id).getAddresses(); 
   	}
	
	@RequestMapping(method = RequestMethod.POST)   
   	public List<Address> addAddress(@PathVariable("userId") long id, @RequestBody final Address address) {
		User user = userDao.findById(id);		
		user.getAddresses().add(address);
		address.setUser(user);
		addressDao.save(address);
		userDao.save(user);
    	return user.getAddresses();
   	}
    
	@RequestMapping(value ="/{addressId}", method = RequestMethod.PUT)//Order of params is important: PathVariable must stand before @RequestBody
   	public List<Address> updateAddress(@PathVariable("userId") long userId, @PathVariable("addressId") long addressId, @RequestBody final Address address) {
    	address.setId(addressId);
    	address.setUser(userDao.findById(userId));
    	addressDao.save(address);
    	return userDao.findById(userId).getAddresses();
   	} 
	
	@RequestMapping(value ="/{addressId}", method = RequestMethod.DELETE)
   	public List<Address> deleteAddress(@PathVariable("userId") long userId, @PathVariable("addressId") long addressId) {    	 
    	addressDao.delete(addressId);
    	return userDao.findById(userId).getAddresses();
   	} 
	
    /*
	@RequestMapping(value ="/{addressId}", method = RequestMethod.GET)//User PathVar rather than PathParam
   	public Address getOneAddress(@PathVariable("addressId") long id) {
    	return (Address) addressDao.findById(id);
   	}
    
	@RequestMapping(value ="/{addressId}", method = RequestMethod.DELETE)
   	public List<Address> deleteAddresses(@PathVariable("addressId") long id) {
    	addressDao.delete(id);
    	return (List<Address>) addressDao.findAll();
   	}
    
	
	
	@RequestMapping(value = "/addressOne", method = RequestMethod.GET)
   	public Address  getAddressOne() {
    	List<Address> allAddresses = new ArrayList<Address>();
		allAddresses = (List<Address>) addressDao.findAll();
		return allAddresses.get(0);
   	}  
   	*/  
}