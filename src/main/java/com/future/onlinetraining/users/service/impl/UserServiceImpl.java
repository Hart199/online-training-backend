package com.future.onlinetraining.users.service.impl;

import com.future.onlinetraining.users.model.User;
import com.future.onlinetraining.users.repository.UserRepository;
import com.future.onlinetraining.users.service.UserService;
import com.future.onlinetraining.utility.ResponseHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    BCryptPasswordEncoder encoder;

    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(s);

        if(user == null){
            throw new UsernameNotFoundException("Can't find user with email: "+s);
        }

        return user;
    }

    public User createOrUpdate(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public User getUserFromSession() {
        if(SecurityContextHolder.getContext().getAuthentication() == null){
            return null;
        }
        else if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof User){
            return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        }
        return null;
    }

    public Page<User> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public ResponseEntity unauthenticated(){
        return new ResponseHelper<>()
                .setSuccessStatus(false)
                .setHttpStatus(HttpStatus.UNAUTHORIZED)
                .setMessage("Anda belum login")
                .send();
    }
}
