package com.future.onlinetraining.users.service.impl;

import com.future.onlinetraining.dto.ChangePasswordDTO;
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
import java.util.Optional;


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

    public User profile() {
        return getUserFromSession();
    }

    public void delete(int id) {
        userRepository.deleteById(id);
    }

    public User changePassword(ChangePasswordDTO changePasswordDTO) {
        if (!changePasswordDTO.getNewPassword().equals(changePasswordDTO.getConfirmPassword()))
            throw new RuntimeException("Konfirmasi password salah.");
        if (!encoder.matches(changePasswordDTO.getCurrentPassword(), getUserFromSession().getPassword()))
            throw new RuntimeException("Password salah.");

        String encodedPassword = encoder.encode(changePasswordDTO.getNewPassword());
        User user = getUserFromSession();
        user.setPassword(encodedPassword);
        return userRepository.save(user);
    }

    public User edit(Integer id, UserDTO userDTO) {
        User user;
        if (id != null) {
            Optional<User> userOptional = userRepository.findById(id);
            if (!userOptional.isPresent())
                throw new NullPointerException("User tidak ditemukan.");

            Role role = roleRepository.findByValue(userDTO.getRole());
            if (role == null)
                throw new NullPointerException("Role tidak ditemukan.");

            user = userOptional.get();
            user.setRole(role);
        } else {
            user = getUserFromSession();
        }

        user.setFullname(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setPhone(userDTO.getPhone());

        return userRepository.save(user);
    }

    public ResponseEntity unauthenticated(){
        return new ResponseHelper<>()
                .setSuccessStatus(false)
                .setHttpStatus(HttpStatus.UNAUTHORIZED)
                .setMessage("Anda belum login")
                .send();
    }
}
