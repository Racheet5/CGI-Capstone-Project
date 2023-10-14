package com.group6.Muzix.Services;

import com.group6.Muzix.Beans.Favourite;
import com.group6.Muzix.Beans.Music;
import com.group6.Muzix.Repository.FavouriteRepository;
import com.group6.Muzix.Repository.MusicRepository;
import com.group6.Muzix.exception.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class FavouriteService {
    @Autowired
    private FavouriteRepository favouriteRepository;
    @Autowired
    private MusicRepository musicRepository;
    public ResponseEntity<?> addNewFavourite(Favourite favourite) {
        try{
            if(Objects.nonNull(favouriteRepository.findOneByMusicId(favourite.getMusicId()))){
                throw new CustomException("Music not found");
            }
            Music music1 = musicRepository.findOneByMusicId(favourite.getMusicId());
            Favourite newFavData = new Favourite(favourite.getUserId(),favourite.getMusicId(), music1.getTitle(),music1.getGenre(), music1.getAlbum(), music1.getArtist());
            Favourite newFavourite = favouriteRepository.save(newFavData);
            return new ResponseEntity<>(newFavourite,HttpStatus.CREATED);

        }catch (CustomException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<?> getAllFavouriteMusic(Integer userId) {
        try{
            List<Favourite> favourites = favouriteRepository.findAllByUserId(userId);
            if(favourites.isEmpty()){
                throw new CustomException("No music added to favourite playlist");
            }else{
                return ResponseEntity.ok(favourites);
            }
        }catch (CustomException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    public ResponseEntity<?> removeMusicFromFavourite(int favId) {
        try{
            if(Objects.nonNull(favouriteRepository.findOneById(favId))){
                favouriteRepository.deleteById(favId);
                return ResponseEntity.ok("MusicId:" + favId + " removed from favourites");
            }else{
                throw new CustomException("MusicId:" + favId + " not found in favourites");
            }
        }catch (CustomException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
