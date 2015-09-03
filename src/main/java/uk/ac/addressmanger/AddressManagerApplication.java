package uk.ac.addressmanger;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;

import uk.ac.addressmanger.dataservice.AddressDao;
import uk.ac.addressmanger.model.Address;

@SpringBootApplication
@RestController
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AddressManagerApplication{

	@Autowired
	private AddressDao addressDao;
	
	public static void main(String[] args) {
        SpringApplication.run(AddressManagerApplication.class, args);
    }
    //http://stackoverflow.com/questions/8597902/spring-security-user-account-registration-creation-and-management
    //http://www.coderanch.com/t/599790/ORM/databases/User-Registration-Login-Hibernate
    //http://jasonwatmore.com/post/2015/03/10/AngularJS-User-Registration-and-Login-Example.aspx
    
    @RequestMapping(value="/userAu", method = RequestMethod.GET)
    public String getUser()
    {
    	return "hello user";
    }
    
    @RequestMapping(value = "/addresses", method = RequestMethod.GET)
   	public List<Address>  getAddresses() {
    	List<Address> allAddresses = new ArrayList<Address>();
		allAddresses = (List<Address>) addressDao.findAll();
		return allAddresses;
   	}
    
    @RequestMapping(value = "/addresses", method = RequestMethod.POST)    
   	public List<Address> addAddresses(@RequestBody final Address address) {
    	addressDao.save(address);
    	return (List<Address>) addressDao.findAll();
   	}
    //@RequestMapping(value ="/removeUserRole/{roleId}", method = RequestMethod.DELETE)
    //public void removeUserRole(@PathVariable("roleId")Long roleId){
    //@DELETE
    //@RequestMapping("/addresses")        
    //@Path("/{addressId}")
    @RequestMapping(value ="/addresses/{addressId}", method = RequestMethod.DELETE)
    //User PathVar rather than PathParam
   	public List<Address> deleteAddresses(@PathVariable("addressId") long id) {
    	addressDao.delete(id);
    	return (List<Address>) addressDao.findAll();
   	}
    
    @RequestMapping(value ="/addresses/{addressId}", method = RequestMethod.PUT)
    //Order of params is important: PathVariable must stand before @RequestBody
   	public List<Address> updateAddresses(@PathVariable("addressId") long id, @RequestBody final Address address) {
    	address.setId(id);
    	addressDao.save(address);
    	return (List<Address>) addressDao.findAll();
   	}
    
    @RequestMapping("/user")
	public Principal user(Principal user) {
		return user;
	}

	@RequestMapping("/resource")
	public Map<String, Object> home() {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("id", UUID.randomUUID().toString());
		model.put("content", "Hello World");
		return model;
	}

	
	@Configuration
	@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
	protected static class SecurityConfiguration extends WebSecurityConfigurerAdapter {
		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.httpBasic().and().authorizeRequests()
					.antMatchers("/index.html", "/home.html", "/login.html", "/").permitAll().anyRequest()
					.authenticated().and().csrf()
					.csrfTokenRepository(csrfTokenRepository()).and()
					.addFilterAfter(csrfHeaderFilter(), CsrfFilter.class);
		}

		private Filter csrfHeaderFilter() {
			return new OncePerRequestFilter() {
				@Override
				protected void doFilterInternal(HttpServletRequest request,
						HttpServletResponse response, FilterChain filterChain)
						throws ServletException, IOException {
					CsrfToken csrf = (CsrfToken) request.getAttribute(CsrfToken.class
							.getName());
					if (csrf != null) {
						Cookie cookie = WebUtils.getCookie(request, "XSRF-TOKEN");
						String token = csrf.getToken();
						if (cookie == null || token != null
								&& !token.equals(cookie.getValue())) {
							cookie = new Cookie("XSRF-TOKEN", token);
							cookie.setPath("/");
							response.addCookie(cookie);
						}
					}
					filterChain.doFilter(request, response);
				}
			};
		}

		private CsrfTokenRepository csrfTokenRepository() {
			HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
			repository.setHeaderName("X-XSRF-TOKEN");
			return repository;
		}
	}
}
