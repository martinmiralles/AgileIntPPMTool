package com.martin.ppmtool.services;

import com.martin.ppmtool.domain.User;
import com.martin.ppmtool.repositories.UserRepository;
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

        //Make sure that password and confirmPassword match

        //Don't persis / show the confirmPassword
        return userRepository.save(newUser);
    }

}
