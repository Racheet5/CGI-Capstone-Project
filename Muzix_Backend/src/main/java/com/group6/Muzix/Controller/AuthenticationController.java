package com.group6.Muzix.Controller;

import com.group6.Muzix.AuthConfig.JwtUtil;
import com.group6.Muzix.Beans.User;
import com.group6.Muzix.Dto.AuthRequest;
import com.group6.Muzix.Services.AuthenticationSerice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@RestController
@CrossOrigin
@RequestMapping("/user")
public class AuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private AuthenticationSerice userSerice;
    
    @GetMapping
    public String message() {
    	return "Iam running";
    }

    @PostMapping("/signup")
    public ResponseEntity<?> userSignUp(@RequestBody User userData){
        return userSerice.registerNewUser(userData);
    }
    @PostMapping("/signin")
    public ResponseEntity<?> generateToken(@RequestBody AuthRequest authRequest) throws Exception {
        try{
            String pwd = hashPassword(authRequest.getPassword());
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUserName(),pwd));
        }catch(Exception e){
            throw new Exception("Invalid UserName and Password");
        }
        return jwtUtil.generateToken(authRequest.getUserName());
    }
    private String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(password.getBytes());
        byte[] digest = md.digest();
        String passwordHash = DatatypeConverter.printHexBinary(digest).toUpperCase();
        return passwordHash;
    }

}
