package demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.web.WebAppConfiguration;

import uk.ac.addressmanger.AddressManagerApplication;

import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = AddressManagerApplication.class)
@WebAppConfiguration
public class AddressManagerApplicationTests {

	@Test
	public void contextLoads() {
	}

}
