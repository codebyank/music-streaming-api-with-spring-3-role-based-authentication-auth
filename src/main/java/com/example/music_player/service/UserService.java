package com.example.music_player.service;

import com.example.music_player.dao.PlaylistRepo;
import com.example.music_player.dao.UserRepo;
import com.example.music_player.dto.UserDto;
import com.example.music_player.model.Playlist;
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
    @Autowired
    PlaylistRepo playlistRepo;
    @Autowired
    PlaylistService playlistService;

    public void saveUser(User newUser) {
        String password= passwordEncoder.encode(newUser.getPassword());
        newUser.setPassword(password);
        userRepo.save(newUser);
    }

    public List<User> getAll() {
        return  userRepo.findAll();
    }

    public String deleteUser(int id) {

        if(userRepo.existsById(id)){
            List<Playlist> playlists=playlistRepo.findByUserId(id);
             if(!playlists.isEmpty()){
                for(Playlist p:playlists){
                    playlistService.deleteById(p.getPlaylistId());
                }
             }
            userRepo.deleteById(id);
             return "deleted";
        }


        return "user not exist";
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

