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
import uk.ac.addressmanger.model.Address;

@RestController
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequestMapping("/addresses")
public class AddressResource {
	@Autowired
	private AddressRepository addressDao;
	
	@RequestMapping(value = "/addressOne", method = RequestMethod.GET)
   	public Address  getAddressOne() {
    	List<Address> allAddresses = new ArrayList<Address>();
		allAddresses = (List<Address>) addressDao.findAll();
		return allAddresses.get(0);
   	}
    
	@RequestMapping(method = RequestMethod.GET)
   	public List<Address>  getAddresses() {
    	List<Address> allAddresses = new ArrayList<Address>();
		allAddresses = (List<Address>) addressDao.findAll();
		return allAddresses;
   	}
    
	@RequestMapping(method = RequestMethod.POST)   
   	public List<Address> addAddresses(@RequestBody final Address address) {
    	addressDao.save(address);
    	return (List<Address>) addressDao.findAll();
   	}
    
	@RequestMapping(value ="/{addressId}", method = RequestMethod.GET)//User PathVar rather than PathParam
   	public Address getOneAddress(@PathVariable("addressId") long id) {
    	return (Address) addressDao.findById(id);
   	}
    
	@RequestMapping(value ="/{addressId}", method = RequestMethod.DELETE)
   	public List<Address> deleteAddresses(@PathVariable("addressId") long id) {
    	addressDao.delete(id);
    	return (List<Address>) addressDao.findAll();
   	}
    
	@RequestMapping(value ="/{addressId}", method = RequestMethod.PUT)//Order of params is important: PathVariable must stand before @RequestBody
   	public List<Address> updateAddresses(@PathVariable("addressId") long id, @RequestBody final Address address) {
    	address.setId(id);
    	addressDao.save(address);
    	return (List<Address>) addressDao.findAll();
   	}
}