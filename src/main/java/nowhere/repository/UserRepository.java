package nowhere.repository;

import nowhere.entity.User;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository extends BaseRepository<User> {

    public UserRepository() {
        super(User.class);
    }
}
