package tanomenu.config;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import tanomenu.repository.UserRepository;

@Service
public class AuthUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public AuthUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public AuthUserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .map(u -> {
                    System.out.println("encontrei o usuário " + u.getEmail() + "com a senha " + u.getPassword() + "uuid " + u.getUuid());
                    return new AuthUserDetails(u);
                })
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
    }
}
