package nowhere.service;

import java.util.Objects;
import nowhere.entity.Role;
import nowhere.repository.BaseRepository;
import nowhere.repository.RoleRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class RoleService extends BaseService<Role, RoleRepository> {

    public static final String ADMIN_ROLE_NAME = "Admin";
    public static final String USER_ROLE_NAME = "User";

    public <T, R extends BaseRepository<T>> RoleService(BaseService<T, R> baseService) {
        super(baseService, new RoleRepository());
    }

    public RoleService() {
        super(new RoleRepository());
    }

    public Role getAdminRole() {
        return findOrCreateRole(ADMIN_ROLE_NAME);
    }

    public Role getUserRole() {
        return findOrCreateRole(USER_ROLE_NAME);
    }

    public static boolean isAdmin(Role role) {
        assert !Objects.isNull(role);

        return ADMIN_ROLE_NAME.equals(role.getName());
    }

    private Role findOrCreateRole(String roleName) {
        assert !StringUtils.isEmpty(roleName);

        return executeQuery(s -> findByName(roleName).orElseGet(() -> persist(new Role().setName(roleName))));
    }
}
