package com.example.music_player.service;

import com.example.music_player.dao.UserRepo;
import com.example.music_player.dto.UserDto;
import com.example.music_player.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService  {
    @Autowired
    UserRepo userRepo;
    @Autowired
    PasswordEncoder passwordEncoder;
    public void saveUser(User newUser) {
        String password= passwordEncoder.encode(newUser.getPassword());
        newUser.setPassword(password);
        userRepo.save(newUser);
    }

    public List<User> getAll() {
        return  userRepo.findAll();
    }

    public String deleteUser(int id) {

        if(!userRepo.existsById(id)){
            return "user not exist";
        }
        userRepo.deleteById(id);
        return "deleted";
    }

    public User getUser(int id) {

        if(userRepo.existsById(id)){
            return userRepo.findById(id).get();
        }
        return null;
    }


    public String update(int id, UserDto userDto) {
        if(!userRepo.existsById(id)){
            return "user doesn't exist";
        }
        User user=userRepo.findById(id).get();
        user.setUsername(userDto.getUsername());
        user.setRoles(userDto.getRoles());
        user.setFirstName(userDto.getFirstname());
        user.setPassword(userDto.getPassword());
        user.setAge(user.getAge());
        user.setLastName(userDto.getLastname());
        user.setEmail(user.getEmail());
        userRepo.save(user);
        return "user updated";
    }
}
