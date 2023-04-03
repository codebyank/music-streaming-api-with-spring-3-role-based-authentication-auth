package com.example.music_player.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name="tbl_song")
@JsonIgnoreProperties(value= {"handler","hibernateLazyInitializer","FieldHandler"})
public class Song {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int songId;
    private  String songName;
    private String genre;
    private Timestamp releaseDate;
    private Timestamp updatedDate;
    private String title;
    private String singer;


    @JoinColumn(name="playlist_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Playlist playlist;


}
