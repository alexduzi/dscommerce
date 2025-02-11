package com.alexduzi.dscommerce.services;

import com.alexduzi.dscommerce.entities.Role;
import com.alexduzi.dscommerce.entities.User;
import com.alexduzi.dscommerce.projections.UserDetailsProjection;
import com.alexduzi.dscommerce.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<UserDetailsProjection> result = repository.searchUserAndRolesByEmail(username);

        if (result.isEmpty()) throw new UsernameNotFoundException("User not found!");

        User user = new User();
        user.setEmail(username);
        user.setPassword(result.get(0).getPassword());
        result.forEach(x -> user.addRole(new Role(x.getRoleId(), x.getAuthority())));

        return user;
    }
}
