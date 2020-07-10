package nowhere.repository;

import nowhere.entity.Role;
import org.springframework.stereotype.Repository;

@Repository
public class RoleRepository extends BaseRepository<Role> {

    public RoleRepository() {
        super(Role.class);
    }
}
