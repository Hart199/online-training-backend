package com.future.onlinetraining.users.service.impl;

import com.future.onlinetraining.dto.UserDTO;
import com.future.onlinetraining.users.model.Role;
import com.future.onlinetraining.users.model.User;
import com.future.onlinetraining.users.repository.RoleRepository;
import com.future.onlinetraining.users.repository.UserRepository;
import com.future.onlinetraining.users.service.UserService;
import com.future.onlinetraining.utility.ResponseHelper;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;


@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;

    @Autowired
    BCryptPasswordEncoder encoder;

    private final String[] roles = {"ADMIN", "TRAINER", "TRAINEE"};

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

    public Page<User> findAll(Pageable pageable, String role, String name) {
        if (role != null) {
            role = role.toUpperCase();
            if (!Arrays.asList(this.roles).contains(role))
                role = null;
        }
        return userRepository.findAllBySearchTerm(pageable, role, name);
    }

    public User create(UserDTO user) {
        try {
            if (!Arrays.asList(this.roles).contains(user.getRole().toUpperCase()))
                throw new RuntimeException("Role tidak ditemukan.");
        } catch (NullPointerException e) {
            throw new NullPointerException("Role tidak boleh kosong.");
        }

        Role role = roleRepository.findByValue(user.getRole().toUpperCase());

        String password = "12345678";
        User newUser = User.builder()
                .email(user.getEmail())
                .fullname(user.getName())
                .role(role)
                .phone(user.getPhone())
                .password(password)
                .build();
        return userRepository.save(newUser);
    }

    public ResponseEntity unauthenticated(){
        return new ResponseHelper<>()
                .setSuccessStatus(false)
                .setHttpStatus(HttpStatus.UNAUTHORIZED)
                .setMessage("Anda belum login")
                .send();
    }
}
