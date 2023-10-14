package com.group6.Muzix.Services;

import com.group6.Muzix.Beans.Favourite;
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
public class AdminService {

    @Autowired
    private MusicRepository musicRepository;

    public ResponseEntity<?> getMusicById(Integer music_id) {
        try {
            Music music = musicRepository.findOneByMusicId(music_id);
            if(Objects.nonNull(music)){
                return ResponseEntity.ok(music);
            }else{
                throw new CustomException("No music found with musicId : " + music_id);
            }
        }catch (CustomException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<?> AddNewMusic(Music music) {
        try{
            if(Objects.nonNull(musicRepository.findOneByMusicId(music.getMusicId()))){
                throw new CustomException("Music is already added");
            }
            Music musicNew = musicRepository.save(music);
            return new ResponseEntity<>(musicNew,HttpStatus.CREATED);

        }catch (CustomException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    public ResponseEntity<?> deleteMusicById(Integer music_id) {
        try{
            if(Objects.nonNull(musicRepository.findOneByMusicId(music_id))){
                musicRepository.deleteById(music_id);
                return ResponseEntity.ok("{\"message\":\"MusicId:" + music_id + " deleted successfully\"}");
            }else{
                throw new CustomException("MusicId:" + music_id + " not found");
            }
        }catch (CustomException e){
            return new ResponseEntity<>("{\"error\":\"" + e.getMessage() + "\"}", HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<?> getAllAdminMusic() {
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

    public ResponseEntity<?> upadteMusicById(Integer musicId, Music musicData) {
        try {
            Music musicOld = musicRepository.findOneByMusicId(musicId);
            if(Objects.nonNull(musicOld)){
                musicOld.setTitle(musicData.getTitle());
                musicOld.setAlbum(musicData.getAlbum());
                musicOld.setArtist(musicData.getArtist());
                musicOld.setGenre(musicData.getGenre());
                Music musicUpdated = musicRepository.save(musicOld);
                return ResponseEntity.ok(musicUpdated);
            }else{
                throw new CustomException("No music found by musicId : " + musicId);
            }
        }catch (CustomException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
