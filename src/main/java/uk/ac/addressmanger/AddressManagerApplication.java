package uk.ac.addressmanger;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import uk.ac.addressmanger.dataservice.UserRepository;
import uk.ac.addressmanger.model.Address;
import uk.ac.addressmanger.model.ReturnMessage;
import uk.ac.addressmanger.model.User;


@SpringBootApplication
@RestController
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AddressManagerApplication{
	@Autowired
	private UserRepository userDao;
	
	public static void main(String[] args) {
        SpringApplication.run(AddressManagerApplication.class, args);
    }
    
    @RequestMapping("/user")//login
	//public Principal user(Principal user) {
    public ReturnMessage user(Principal user) {
    	if(user!=null)
    	{
    		User loggingUser = userDao.findByUsername(user.getName());
    		return new ReturnMessage(loggingUser.getId(),loggingUser.getUsername(),loggingUser.getEmail());
    	}
    	else
    		return new ReturnMessage(-1,"Invalid Username/password","");
	}
    @RequestMapping(value = "/user", method = RequestMethod.POST) //register   
   	public ReturnMessage addUser(@RequestBody final User user) {
    	User newUser = User.createUser(user.getUsername(), user.getEmail(), user.getPassword());
    	newUser = userDao.save(newUser);
    	if(newUser != null)
    		return new ReturnMessage(1,"Success","Congratulations. You've registered successfully. Please click on Login to start.");
    	else
    		return new ReturnMessage(0,"Failed","Sorry. Oops, there were somethings wrong. Please register again.");
    	
   	} 

	@RequestMapping("/resource")
	public Map<String, Object> home() {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("id", UUID.randomUUID().toString());
		model.put("content", "Hello World");
		return model;
	}
	
	@RequestMapping(value = "/addressOne", method = RequestMethod.GET)
   	public Address  getAddressOne() {    	
		return new Address("21","Connaught Road", "Lancaster", "LA 14BQ", "UK");
   	}  
}

