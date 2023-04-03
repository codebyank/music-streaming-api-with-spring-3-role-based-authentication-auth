package com.example.music_player.dto;


import lombok.Data;


@Data
public class SongDto {

    String songname;
    String genre;
    String title;
    String singer;

    String playlistId;

}
