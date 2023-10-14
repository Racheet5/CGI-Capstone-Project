package com.group6.Muzix.Beans;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Music {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int musicId;
    private String title;
    private String genre;
    private String album;
    private String artist;

    public int getMusicId() {
        return musicId;
    }

    public void setMusicId(int musicId) {
        this.musicId = musicId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }
    public Music(){}
    public Music(String title, String genre, String album, String artist) {
        this.title = title;
        this.genre = genre;
        this.album = album;
        this.artist = artist;
    }

    public Music(int musicId, String title, String genre, String album, String artist) {
        this.musicId = musicId;
        this.title = title;
        this.genre = genre;
        this.album = album;
        this.artist = artist;
    }
}
