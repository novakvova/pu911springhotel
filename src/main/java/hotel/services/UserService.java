package hotel.services;

import hotel.entities.UserEntity;
import hotel.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;


@Service
@Transactional
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserEntity user = userRepository.findByUsername(username);
        if(user==null)
            throw new UsernameNotFoundException("Email "+ username +" not found");
        return new org.springframework
                .security.core.userdetails.User(user.getUsername(),
                user.getPassword(), getAuthorities(user));
    }

    private static Collection<? extends GrantedAuthority> getAuthorities(UserEntity user) {
        String [] userRoles = user.getRoles().stream()
                .map((role) -> role.getName()).toArray(String []:: new);
        Collection<GrantedAuthority> authorityCollections =
                AuthorityUtils.createAuthorityList(userRoles);
        return authorityCollections;
    }
}
