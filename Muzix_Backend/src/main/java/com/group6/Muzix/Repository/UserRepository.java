package com.group6.Muzix.Repository;

import com.group6.Muzix.Beans.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Integer> {
    User findByUserName(String username);
    User findByEmail(String email);
}
