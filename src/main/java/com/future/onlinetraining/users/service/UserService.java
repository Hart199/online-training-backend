package com.future.onlinetraining.users.service;

import com.future.onlinetraining.users.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService  extends UserDetailsService {
    User createOrUpdate(User user);
    User getUserFromSession();
    Page<User> findAll(Pageable pageable, String role);
    ResponseEntity unauthenticated();
}
