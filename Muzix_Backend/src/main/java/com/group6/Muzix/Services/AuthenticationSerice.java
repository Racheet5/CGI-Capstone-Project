package com.group6.Muzix.Services;

import com.group6.Muzix.Beans.User;
import com.group6.Muzix.exception.CustomException;
import com.group6.Muzix.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

@Service
public class AuthenticationSerice {

    @Autowired
    private UserRepository userRepository;

    public ResponseEntity<?> registerNewUser(User userData) {
        try{
            if (Objects.nonNull(userRepository.findByEmail(userData.getEmail()))) {
                throw new CustomException("User already present");
            }
            String encryptedPassword = userData.getPassword();

            try{
                encryptedPassword = hashPassword(userData.getPassword());

            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }

            User userNew = new User(userData.getUserName(), userData.getEmail(), encryptedPassword, userData.getImageUrl(), userData.getRole());
            User user = userRepository.save(userNew);
            return new ResponseEntity<>(user, HttpStatus.CREATED);

        }catch (CustomException e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.CONFLICT); //409
        }
    }

    private String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(password.getBytes());
        byte[] digest = md.digest();
        String passwordHash = DatatypeConverter.printHexBinary(digest).toUpperCase();
        return passwordHash;
    }
}
