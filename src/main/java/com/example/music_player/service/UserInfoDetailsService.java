package com.example.music_player.service;

import com.example.music_player.dao.UserRepo;
import com.example.music_player.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserInfoDetailsService implements UserDetailsService {
    @Autowired
    UserRepo userRepo;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<User> users=userRepo.findByUserName(username);
        if(users.isEmpty()){
            throw  new UsernameNotFoundException("user not exist");
        }
        User user=users.get(0);
        return new UserInfoDetails(user);
    }
}
