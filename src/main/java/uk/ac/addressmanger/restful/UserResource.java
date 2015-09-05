package uk.ac.addressmanger.restful;

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
import uk.ac.addressmanger.dataservice.UserRepository;
import uk.ac.addressmanger.model.User;

@RestController
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequestMapping("/users")
public class UserResource {
	@Autowired
	private UserRepository userDao;
	
	@RequestMapping(method = RequestMethod.GET)
    public List<User> getUsers()
    {
    	List<User> allUsers = (List<User>) userDao.findAll();
    	return allUsers;
    }	
}
