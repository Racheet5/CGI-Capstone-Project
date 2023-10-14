package com.group6.Muzix.Repository;

import com.group6.Muzix.Beans.Favourite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FavouriteRepository extends JpaRepository<Favourite,Integer> {
    Favourite findOneByMusicId(int musicId);

    Favourite findOneById(int favId);

    List<Favourite> findAllByUserId(Integer user_id);
}
