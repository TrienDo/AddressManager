package uk.ac.addressmanger.dataservice;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import uk.ac.addressmanger.model.Address;
import uk.ac.addressmanger.model.User;

@Repository
@Qualifier(value = "userRepository")
public interface UserRepository extends CrudRepository<User, Long> {
    public User findByUsername(String username);
    public User findById(long id);
}
