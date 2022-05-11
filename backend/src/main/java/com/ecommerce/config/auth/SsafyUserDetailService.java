package com.ecommerce.config.auth;

import com.ecommerce.config.auth.SsafyUserDetails;
import com.ecommerce.domain.repository.entity.DABOUser;
import com.ecommerce.infrastructure.repository.DABOUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class SsafyUserDetailService implements UserDetailsService {
    @Autowired
    DABOUserRepository daboUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<DABOUser> user = daboUserRepository.findDABOUserByEmail(username);

        if(user != null) {
            SsafyUserDetails userDetails = new SsafyUserDetails(user.get());
            return userDetails;
        }
        return null;
    }
}
