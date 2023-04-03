package com.example.music_player.controller;

import com.example.music_player.dao.UserRepo;
import com.example.music_player.dto.UserDto;
import com.example.music_player.model.User;
import com.example.music_player.service.UserService;
import com.example.music_player.util.CommonValidator;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value="api/v1/user")
public class UserController {
    @Autowired
    UserService service;
    @Autowired
    UserRepo repo;

    @PostMapping(value="/add-user")
    public ResponseEntity<String> addUser(@RequestBody UserDto userDto){


        JSONObject jsonObject=new JSONObject();
        jsonObject.put("roles",userDto.getRoles());
        jsonObject.put("username",userDto.getUsername());
        jsonObject.put("firstname",userDto.getFirstname());
        jsonObject.put("lastname",userDto.getLastname());
        jsonObject.put("password",userDto.getPassword());
        jsonObject.put("email",userDto.getEmail());
        jsonObject.put("age",userDto.getAge());
        JSONObject errorList=validateUser(jsonObject);
        if(errorList.isEmpty()){
            User newUser=setUser(jsonObject);
            newUser.setRoles(userDto.getRoles());
            service.saveUser(newUser);
            return new ResponseEntity<>("user saved", HttpStatus.OK);
        }
        return new ResponseEntity<>(errorList.toString(),HttpStatus.BAD_REQUEST);

    }
    @GetMapping(value="/get")
    public List<User> getAll(){
        return   service.getAll();
    }
    @GetMapping(value="/getById")
    public ResponseEntity<String> getUser(@RequestParam int id){
        User user=service.getUser(id);
        if(user==null){
            return new ResponseEntity<>("user doesn't exist",HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(user.toString(),HttpStatus.OK);
    }
    @PutMapping(value = "/update")
    public String update(@RequestParam int id,@RequestBody UserDto userDto){
        return service.update(id,userDto);
    }
    @DeleteMapping(value="/delete")
    public String deleteUser(@RequestParam int id){
        return service.deleteUser(id);
    }
    public User setUser(JSONObject jsonObject){
        User user=new User();
        user.setUsername(jsonObject.getString("username"));
        user.setPassword(jsonObject.getString("password"));

        user.setFirstName(jsonObject.getString("firstname"));
        user.setEmail(jsonObject.getString("email"));
        if(jsonObject.has("age")) {
            user.setAge(jsonObject.getInt("age"));
        }
        if(jsonObject.has("lastname")) {
            user.setLastName(jsonObject.getString("lastname"));
        }
        return user;
    }
    public JSONObject validateUser(JSONObject jsonObject){
        JSONObject error=new JSONObject();
        List<User> user=repo.findByUserName(jsonObject.getString("username"));
        if(!user.isEmpty()){
            error.put("username","already exist");
            return error;
        }
        if(!jsonObject.has("username")){
            error.put("username","missing parameter");
        }
        if(!jsonObject.has("password")){
            error.put("password","missing parameter");
        }

        if(!jsonObject.has("firstname")){
            error.put("firstname","missing parameter");
        }
        if(!jsonObject.has("roles")){
            error.put("roles","missing parameters");
        }
        if(jsonObject.has("email")){
            if(!CommonValidator.isValidEmail(jsonObject.getString("email"))){
                error.put("email","not valid email");
            }
        }
        else {
            error.put("email","missing parameters");
        }

        return error;
    }
}
