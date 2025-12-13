package com.auth.securityconf;

import com.auth.entity.UserObj;
import com.auth.repo.UserObjRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserObjRepo userObjRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("username    "+ username);
        UserObj userObj = userObjRepo.findByEmail(username).orElseThrow(()->new UsernameNotFoundException("Username not found"));
        String userName = userObj.getEmail();
        String password = userObj.getPassword();
        List<GrantedAuthority> roles = userObj.getRoles().stream().collect(Collectors.toUnmodifiableList());
        return new User(username,password,roles);
    }
}
