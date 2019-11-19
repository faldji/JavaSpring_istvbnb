package com.istv.progcomp.service;

import com.istv.progcomp.model.UserEntity;
import com.istv.progcomp.data.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserAuthServ implements UserDetailsService {
    @Autowired
    private  UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findUserEntityByUsername(username);
        if (user == null) {
            user = userRepository.findUserEntityByEmail(username);
            if (user==null)
                throw new UsernameNotFoundException("No user present with username/Email : " + username);
        }
        return new User(user.getUsername(),user.getPassword(),true,true,true,true,user.getAuthorities());
    }
}
