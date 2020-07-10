package nowhere.service;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import javax.annotation.Resource;
import lombok.Setter;
import nowhere.entity.Role;
import nowhere.entity.User;
import nowhere.repository.BaseRepository;
import nowhere.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class UserService extends BaseService<User, UserRepository> implements UserDetailsService {
    @Setter(onMethod_=@Autowired)
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public <T, R extends BaseRepository<T>> UserService(BaseService<T, R> baseService) {
        super(baseService, new UserRepository());
    }

    public UserService() {
        super(new UserRepository());
    }

    public boolean isAdmin(User user) {
        assert !Objects.isNull(user);

        return RoleService.isAdmin(user.getRole());
    }

    public User createUser(String name, String passw) {
        return createUser(name, passw, false);
    }

    public User createAdmin(String name, String passw) {
        return createUser(name, passw, true);
    }

    public User createUser(String name, String passw, boolean admin) {
        assert !StringUtils.isEmpty(name) && !StringUtils.isEmpty(passw);

        return executeQuery(session -> {
            RoleService roleService = new RoleService(this);
            Role role = admin ? roleService.getAdminRole() : roleService.getUserRole();

            return persist(new User().setName(name).setPassword(passwordEncoder.encode(passw)).setRole(role));
        });
    }

    public boolean matchPassword(User user, String password) {
        return passwordEncoder.matches(password, user.getPassword());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return executeQuery(session -> {
            User user = userRepository.findByName(session, username)
                            .orElseThrow(() -> new UsernameNotFoundException(String.format("User with name [%s] not found", username)));

            return org.springframework.security.core.userdetails.User.builder()
                    .username(username)
                    .password(user.getPassword())
                    .disabled(false)
                    .accountExpired(false)
                    .accountLocked(false)
                    .credentialsExpired(false)
                    .roles(user.getRole().getName())
                    .authorities(RoleService.ADMIN_ROLE_NAME.equals(user.getRole().getName()) ? "isAdmin" : "isUser")
                    .build();
        });
    }
}
