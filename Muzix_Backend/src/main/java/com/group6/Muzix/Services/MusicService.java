package com.group6.Muzix.Services;

import com.group6.Muzix.Beans.Music;
import com.group6.Muzix.Repository.MusicRepository;
import com.group6.Muzix.exception.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class MusicService {
    @Autowired
    private MusicRepository musicRepository;

    public ResponseEntity<?> getAllmusicforUser() {
        try {
            List<Music> musicList = musicRepository.findAll();
            if(musicList.isEmpty()){
                throw new CustomException("No music found");
            }else{
                return ResponseEntity.ok(musicList);
            }
        }catch (CustomException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<?> fetchMusicByAlbum(String albumName) {
        try {
            List<Music> musicList = musicRepository.findAllByAlbum(albumName);
            if(musicList.isEmpty()){
                throw new CustomException("No music found with album " + albumName);
            }else{
                return ResponseEntity.ok(musicList);
            }
        }catch (CustomException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<?> fetchMusicByArtist(String artistName) {
        try {
            List<Music> musicList = musicRepository.findAllByArtist(artistName);
            if(musicList.isEmpty()){
                throw new CustomException("No music found with Artist " + artistName);
            }else{
                return ResponseEntity.ok(musicList);
            }
        }catch (CustomException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<?> fetchMusicByGenre(String genreName) {
        try {
            List<Music> musicList = musicRepository.findAllByGenre(genreName);
            if(musicList.isEmpty()){
                throw new CustomException("No music found with Genre " + genreName);
            }else{
                return ResponseEntity.ok(musicList);
            }
        }catch (CustomException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<?> getMusicById(Integer musicId) {
        try {
            Music music = musicRepository.findOneByMusicId(musicId);
            if(Objects.nonNull(music)){
                return ResponseEntity.ok(music);
            }else{
                throw new CustomException("No music found with musicId : " + musicId);
            }
        }catch (CustomException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

//    public List<Music> getAllmusicforUser() {
//        return musicRepository.findAll();
//    }

//    public List<Music> getMusicByAlbum(String albumName) {
//        return musicRepository.findAllByAlbum(albumName);
//    }

//    public List<Music> searchMusicByGenre(String genreName) {
//        return musicRepository.findAllByGenre(genreName);
//    }

//    public List<Music> getMusicByArtist(String artistName) {
//        return musicRepository.findAllByArtist(artistName);
//    }

//    public Music getMusicByid(Integer id) {
//        return musicRepository.findOneByMusicId(id);
//    }
}
