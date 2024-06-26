package Zeus.API.ZEUS.Repository;

import Zeus.API.ZEUS.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserRepository extends JpaRepository<User,Long> {


    User findByLogin(String login);

}

