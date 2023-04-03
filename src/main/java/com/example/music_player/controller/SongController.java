package com.example.music_player.controller;

import com.example.music_player.dao.PlaylistRepo;
import com.example.music_player.dao.SongRepo;
import com.example.music_player.dao.UserRepo;
import com.example.music_player.dto.SongDto;
import com.example.music_player.model.Playlist;
import com.example.music_player.model.Song;
import com.example.music_player.service.SongService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;

@RestController
@RequestMapping(value="api/v1/song")
public class SongController {
    @Autowired
    SongService service;
    @Autowired
    SongRepo songRepo;
    @Autowired
    UserRepo userRepo;
    @Autowired
    PlaylistRepo playlistRepo;
    @PostMapping
    public ResponseEntity<String> addSong(@RequestBody SongDto songDto){

        JSONObject object=new JSONObject();
        object.put("songname",songDto.getSongname());
        object.put("title",songDto.getTitle());
        object.put("singer",songDto.getSinger());
        object.put("genre",songDto.getGenre());
        object.put("playlistId",songDto.getPlaylistId());
        JSONObject error=validSong(object);
        if(error.isEmpty()){
            Song song=setSong(object);
            service.saveSong(song);
            return new ResponseEntity<>("song saved", HttpStatus.OK);
        }
        return new ResponseEntity<>(error.toString(),HttpStatus.BAD_REQUEST);
    }
    @GetMapping(value="/get-song")
    public List<Song> getSong(@Nullable @RequestParam String name){
        return    service.getSong(name);
    }
    @GetMapping(value="/get-songBy-playlist")
    public List<Song> getSongs(@RequestParam String playlistName){
        return service.getSongs(playlistName);
    }
    @DeleteMapping(value="/delete-song")
    public String deleteSong(@RequestParam String name){
        return service.deleteSong(name);
    }
    @PutMapping(value="/update-song")
    public String updateSong(@RequestParam String name,@RequestBody SongDto songDto){
        return service.updateSong(name,songDto);
    }
    private Song setSong(JSONObject object) {
        Song song=new Song();
        song.setSongName(object.getString("songname"));
        song.setGenre(object.getString("genre"));
        song.setTitle(object.getString("title"));
        song.setSinger(object.getString("singer"));

        Timestamp timestamp=new Timestamp(System.currentTimeMillis());
        song.setReleaseDate(timestamp);
        Playlist playlist=playlistRepo.findById(Integer.parseInt(object.getString("playlistId"))).get();
        song.setPlaylist(playlist);
        return song;
    }

    private JSONObject validSong(JSONObject object) {
        List<Song> songList=songRepo.findSongByName(object.getString("songname"));

        JSONObject error=new JSONObject();
        if(!songList.isEmpty()){
            error.put("song","already exist");
            return error;
        }
        if(!object.has("songname")){
            error.put("songname","missing parameters");
        }
        if(!object.has("title")){
            error.put("title","missing parameters");
        }
        if(!object.has("singer")){
            error.put("singer","missing parameters");
        }
        if(!object.has("genre")){
            error.put("genre","missing parameters");
        }


        if(!object.has("playlistId")){

            error.put("playlistId","missing parameter");

        }
        if( !playlistRepo.existsById(Integer.parseInt(object.getString("playlistId"))) ){
           error.put("playlistId","playlist not exist");
        }

        return error;
    }
}
