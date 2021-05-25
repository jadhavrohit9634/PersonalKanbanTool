package com.rjtech.ppmtool.services;

import com.rjtech.ppmtool.domain.User;
import com.rjtech.ppmtool.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public User saveUser(User newUser){
       newUser.setPassword(bCryptPasswordEncoder.encode(newUser.getPassword()));

       //Username has to be unique (exception)

        // Make sure password and confirm password match
        // We dont persist or show the confirmPassword
       return userRepository.save(newUser);
    }
}
