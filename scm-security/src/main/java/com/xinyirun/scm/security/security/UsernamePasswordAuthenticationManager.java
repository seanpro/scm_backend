package com.xinyirun.scm.security.security;

import com.xinyirun.scm.core.system.service.client.user.IMUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Primary
@Component
public class UsernamePasswordAuthenticationManager implements AuthenticationManager {

    @Autowired
    IMUserService userServiceIml;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        UserDetails users = userServiceIml.loadUserByUsername(username);

        if(!new BCryptPasswordEncoder().matches(password, users.getPassword())) {
            throw new BadCredentialsException("the password is wrong");
        }
        Authentication authentication2 = new UsernamePasswordAuthenticationToken(users, users.getPassword(), users.getAuthorities());

        return authentication2;
    }
}