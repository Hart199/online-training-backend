package com.future.onlinetraining.users.service;

import com.future.onlinetraining.users.model.User;
import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService  extends UserDetailsService {
    User createOrUpdate(User user);
    User getUserFromSession();
    List<User> findAll();
}
