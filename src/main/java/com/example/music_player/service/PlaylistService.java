package com.example.music_player.service;

import com.example.music_player.dao.PlaylistRepo;
import com.example.music_player.dao.SongRepo;
import com.example.music_player.dao.UserRepo;
import com.example.music_player.dto.PlaylistDto;
import com.example.music_player.model.Playlist;
import com.example.music_player.model.Song;
import com.example.music_player.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class PlaylistService {
    @Autowired
    PlaylistRepo playlistRepo;
    @Autowired
    UserRepo userRepo;
    @Autowired
    SongRepo songRepo;


    public void savePlaylist(Playlist playlist) {
        playlistRepo.save(playlist);
    }

    public Playlist getBYId(int id) {
        if(playlistRepo.existsById(id)){

           Playlist playlist= playlistRepo.findById(id).get();

           return playlist;
        }
        else
            return null;
    }

    public List<Playlist> getAllplaylist() {
        List<Playlist> playlistList=playlistRepo.findAll();
        return playlistList;
    }

    public String deleteById(int id) {

        if(playlistRepo.existsById(id)){
            List<Song> songList=songRepo.findByPlaylistId(id);
            if(!songList.isEmpty()){
                songRepo.deleteByPlaylistId(id);
            }
            playlistRepo.deleteById(id);
            return "deleted";
        }
        return "playlist not exist";
    }

    public String updateById(int id, PlaylistDto newPlaylist) {
        if(!playlistRepo.existsById(id)){
            return "playlist does not exist";
        }
        Playlist playlist=playlistRepo.findById(id).get();

        playlist.setPlaylistName(newPlaylist.getPlaylistName());

        Timestamp upadatedDate=new Timestamp(System.currentTimeMillis());
        playlist.setUpdatedDate(upadatedDate);
        User user= userRepo.findById(Integer.parseInt(newPlaylist.getUserId())).get();
        playlist.setUser(user);
        playlistRepo.save(playlist);
        return "playlist updated";
    }





}

