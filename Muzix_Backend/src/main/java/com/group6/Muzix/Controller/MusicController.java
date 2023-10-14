package com.group6.Muzix.Controller;

import com.group6.Muzix.Services.MusicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin
@RequestMapping("/muzix")
public class MusicController {

    @Autowired
    private MusicService musicService;

    @GetMapping("/all")
    public ResponseEntity<?> getAllmusicforUser(){
        return musicService.getAllmusicforUser();
    }

    @GetMapping("{music_id}")
    public ResponseEntity<?> getMusicById(@PathVariable("music_id") Integer music_id){
        return musicService.getMusicById(music_id);
    }

    @GetMapping("album/{album_name}")
    public ResponseEntity<?> fetchMusicByAlbum(@PathVariable("album_name") String album_name){
        return musicService.fetchMusicByAlbum(album_name);
    }

    @GetMapping("artist/{artist_name}")
    public ResponseEntity<?> fetchMusicByArtist(@PathVariable("artist_name") String artist_name){
        return musicService.fetchMusicByArtist(artist_name);
    }

    @GetMapping("genre/{genre_name}")
    public ResponseEntity<?> fetchMusicByGenre(@PathVariable("genre_name") String genre_name){
        return musicService.fetchMusicByGenre(genre_name);
    }
}
