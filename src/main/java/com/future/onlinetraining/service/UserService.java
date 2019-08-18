package com.future.onlinetraining.service;

import com.future.onlinetraining.dto.ChangePasswordDTO;
import com.future.onlinetraining.dto.UserDTO;
import com.future.onlinetraining.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.multipart.MultipartFile;

public interface UserService  extends UserDetailsService {
    User createOrUpdate(User user);
    User getUserFromSession();
    Page<User> findAll(Pageable pageable, String role, String name);
    ResponseEntity unauthenticated();
    User create(UserDTO user);
    void delete(int id);
    User changePassword(ChangePasswordDTO changePasswordDTO);
    User profile();
    User edit(Integer id, UserDTO userDTO);
    User editPhoto(MultipartFile multipartFile);
}
