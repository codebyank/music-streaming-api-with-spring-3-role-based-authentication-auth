package com.example.music_player.controller;

import com.example.music_player.dao.PlaylistRepo;
import com.example.music_player.dao.UserRepo;
import com.example.music_player.dto.PlaylistDto;
import com.example.music_player.model.Playlist;
import com.example.music_player.model.User;
import com.example.music_player.service.PlaylistService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;

@RestController
@RequestMapping(value="api/v1/playlist")
public class PlaylistController {
    @Autowired
    PlaylistService playlistService;
    @Autowired
    PlaylistRepo playlistRepo;
    @Autowired
    UserRepo userRepo;
    @PostMapping
    public ResponseEntity<String> addPlaylist(@RequestBody PlaylistDto playlistDto){

        JSONObject object=new JSONObject();
           object.put("userId",playlistDto.getUserId());
           object.put("playlistName",playlistDto.getPlaylistName());
        JSONObject error=validPlaylist(object);
        if(error.isEmpty()){
            Playlist playlist=setPlaylist(object);
            playlistService.savePlaylist(playlist);
            return new ResponseEntity<>("playlist added", HttpStatus.OK);
        }
        return new ResponseEntity<>(error.toString(),HttpStatus.BAD_REQUEST);
    }
    @GetMapping(value="/get-playlist")
    public ResponseEntity<String> getPlaylistById(@RequestParam int id){
        Playlist playlist= playlistService.getBYId(id);
        if (playlist==null){
            return new ResponseEntity<>("playlist not exist",HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(playlist.toString(),HttpStatus.OK);
    }
    @GetMapping(value="/getAll-playlist")
    public List<Playlist> getPlaylist(){
        List<Playlist>playlists=playlistService.getAllplaylist();
        System.out.println(playlists.stream().toList());
        return playlists;
    }
    @DeleteMapping(value="/delete-playlist")
    public String deletePlaylist(@RequestParam int id){
        return  playlistService.deleteById(id);
    }
    @PutMapping(value="/update-playlist")
    public String updatePlaylist(@RequestParam int id,@RequestBody PlaylistDto Playlist){
        return playlistService.updateById(id,Playlist);
    }

    public JSONObject validPlaylist(JSONObject object){
        JSONObject error=new JSONObject();
        List<Playlist> playlistList=playlistRepo.findByName(object.getString("playlistName"));
        if(!playlistList.isEmpty()){
            error.put("playlist","already exist");
            return error;
        }
        if(!object.has("playlistName")){
            error.put("playlistName","missing parameters");
        }
        if(!object.has("userId")){
            error.put("userId","missing parameters");
        }
        if(!userRepo.existsById(Integer.parseInt(object.getString("userId")))){
            error.put("userId","user doesn't exist");
        }
        return error;
    }
    public Playlist setPlaylist(JSONObject object){
        Playlist playlist=new Playlist();
        playlist.setPlaylistName(object.getString("playlistName"));
        User user=userRepo.findById(Integer.parseInt(object.getString("userId"))).get();
        playlist.setUser(user);
        Timestamp createdate=new Timestamp(System.currentTimeMillis());
        playlist.setAddedDate(createdate);
        return playlist;
    }
}
