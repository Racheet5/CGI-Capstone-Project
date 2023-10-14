package com.group6.Muzix.Repository;

import com.group6.Muzix.Beans.Music;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MusicRepository extends JpaRepository<Music,Integer> {
    List<Music> findAllByAlbum(String albumName);

    List<Music> findAllByGenre(String genreName);

    List<Music> findAllByArtist(String artistName);

    Music findOneByMusicId(Integer id);
}
