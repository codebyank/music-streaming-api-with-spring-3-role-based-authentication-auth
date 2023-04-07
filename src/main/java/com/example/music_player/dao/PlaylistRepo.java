package com.example.music_player.dao;

import com.example.music_player.model.Playlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlaylistRepo extends JpaRepository<Playlist,Integer> {
    @Query(value="select * from tbl_playlist where playlist_name=:name ", nativeQuery=true)
    public List<Playlist> findByName(String name);
      @Query(value="select * from tbl_playlist where user_id=:id ", nativeQuery=true)
    public List<Playlist> findByUserId(int id);
}
