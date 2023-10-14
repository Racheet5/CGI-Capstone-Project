package com.group6.Muzix.Controller;

import com.group6.Muzix.Beans.Music;
import com.group6.Muzix.Services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/muzix")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @GetMapping
    public String sayhello(){
        return "You api is passed";
    }

    @GetMapping("admin/all")
    public ResponseEntity<?> getAllMusic(){
        return adminService.getAllAdminMusic();
    }

    @PutMapping("admin/{music_id}")
    public ResponseEntity<?> updateMusicById(@PathVariable("music_id") Integer music_id, @RequestBody Music music){
        return adminService.upadteMusicById(music_id, music);
    }

    @GetMapping("admin/{music_id}")
    public ResponseEntity<?> getMusicbyId(@PathVariable("music_id") Integer music_id){
        return adminService.getMusicById(music_id);
    }

    @PostMapping("admin/add")
    public ResponseEntity<?> AddnewMusic(@RequestBody Music music){
        return adminService.AddNewMusic(music);
    }

    @DeleteMapping("admin/{musicid}")
    public ResponseEntity<?> deleteMusicById(@PathVariable("musicid") Integer musicid){
        return adminService.deleteMusicById(musicid);
    }

}
