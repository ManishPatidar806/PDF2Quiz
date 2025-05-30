package com.backend.internalhackthon.Service;

import com.backend.internalhackthon.Model.Entity.User;
import com.backend.internalhackthon.Repository.AuthRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailServices implements UserDetailsService {

    AuthRepository authRepository;

    public UserDetailServices(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = authRepository.findByEmail(username);
        user.orElseThrow(()->new UsernameNotFoundException("UserName not found"));
        return new UserDetail(user.get());
    }
}
