package com.future.onlinetraining.service.impl;

import com.future.onlinetraining.dto.ChangePasswordDTO;
import com.future.onlinetraining.dto.UserDTO;
import com.future.onlinetraining.entity.Role;
import com.future.onlinetraining.entity.User;
import com.future.onlinetraining.entity.enumerator.ErrorEnum;
import com.future.onlinetraining.repository.RoleRepository;
import com.future.onlinetraining.repository.UserRepository;
import com.future.onlinetraining.service.FileHandlerService;
import com.future.onlinetraining.service.UserService;
import com.future.onlinetraining.utility.ResponseHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.Optional;


@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    FileHandlerService fileHandlerService;

    @Autowired
    BCryptPasswordEncoder encoder;

    private final String[] roles = {"ADMIN", "TRAINER", "TRAINEE"};

    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByEmail(s);

        if(!user.isPresent()){
            throw new UsernameNotFoundException("Can't find user with email: "+s);
        }

        return user.get();
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
                .password(encoder.encode(password))
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
        if (getUserFromSession().getRole().getValue().equals("ADMIN")) {
            Optional<User> userOptional = userRepository.findById(id);
            if (!userOptional.isPresent())
                throw new RuntimeException(ErrorEnum.USER_NOT_FOUND.getMessage());

            Role role = roleRepository.findByValue(userDTO.getRole());
            if (role == null)
                throw new RuntimeException("Role tidak ditemukan.");

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

    public User editPhoto(MultipartFile multipartFile) {
        User user = getUserFromSession();

        fileHandlerService.setDirPath("photos/");
        String hashedFilename = DigestUtils.md5DigestAsHex(
                (user.getEmail()).getBytes());

        String file = fileHandlerService.update(
                user.getPhoto(), hashedFilename + "_" + multipartFile.getOriginalFilename(), multipartFile);
        user.setPhoto(file);

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
