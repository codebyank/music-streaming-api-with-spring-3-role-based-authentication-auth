package com.example.music_player.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class UserDto {

    String username;
    String password;
    List<String> roles=new ArrayList<>();
    String firstname;
    String lastname;
    String email;
    int age;

}
