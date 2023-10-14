package com.group6.Muzix.Controller;

import com.group6.Muzix.Beans.Favourite;
import com.group6.Muzix.Services.FavouriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/muzix")
public class FavouriteController {

    @Autowired
    private FavouriteService favouriteService;

    @PostMapping("/favourite/add")
    public ResponseEntity<?> addMusicToFavourite(@RequestBody Favourite favourite){
//        Favourite favourite1 = favouriteService.addfavourite(favourite);
//        return new ResponseEntity<Favourite>(favourite1, HttpStatus.CREATED);
        return favouriteService.addNewFavourite(favourite);
    }
    @GetMapping("/favourite/{user_id}")
    public ResponseEntity<?> getAllFavouriteMusic(@PathVariable("user_id") Integer user_id){
//        List<Favourite> favourites = favouriteService.getAll();
//        return new ResponseEntity<List<Favourite>>(favourites, HttpStatus.OK);
        return favouriteService.getAllFavouriteMusic(user_id);
    }
    @DeleteMapping("/favourite/{fav_id}")
    public ResponseEntity<?> DeleteById(@PathVariable("fav_id") int fav_id){
//        Boolean status = favouriteService.deletefav(music_id);
//        return new ResponseEntity<>("deleted successfully", HttpStatus.OK);
        return favouriteService.removeMusicFromFavourite(fav_id);
    }
}
