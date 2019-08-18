package com.future.onlinetraining.controller;

import com.future.onlinetraining.dto.ChangePasswordDTO;
import com.future.onlinetraining.dto.UserDTO;
import com.future.onlinetraining.service.UserService;
import com.future.onlinetraining.utility.ResponseHelper;
import com.future.onlinetraining.utility.ValidationHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.Arrays;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/users")
    public ResponseEntity all(@RequestParam(value = "page", defaultValue = "0") int page,
                              @RequestParam(value = "size", defaultValue = "5") int size,
                              @RequestParam(value = "role", required = false) String role,
                              @RequestParam(value = "sortBy", defaultValue = "") String sortParam,
                              @RequestParam(value = "name", required = false) String name) {
        Pageable pageable;
        String[] sortParams = {"fullname", "email", "phone"};
        boolean isSortParamValid = Arrays.asList(sortParams).contains(sortParam.toLowerCase());
        if (!isSortParamValid) {
            pageable = PageRequest.of(page, size);
        } else {
            pageable = PageRequest.of(page, size, Sort.by(sortParam.toLowerCase()).ascending());
        }

        return new ResponseHelper<>()
                .setHttpStatus(HttpStatus.OK)
                .setParam("data", userService.findAll(pageable, role, name))
                .setSuccessStatus(true)
                .send();
    }

    @PostMapping("/_admin/users")
    public ResponseEntity create(@Valid @RequestBody UserDTO userDTO, BindingResult bindingResult) {
        ValidationHandler.validate(bindingResult);
        return new ResponseHelper<>()
                .setParam("data", userService.create(userDTO))
                .send();
    }

    @GetMapping("/users/_profile")
    public ResponseEntity getProfile() {
        return new ResponseHelper<>()
                .setParam("data", userService.profile())
                .send();
    }

    @PutMapping("/users/_change-password")
    public ResponseEntity changePassword(
            @Valid @RequestBody ChangePasswordDTO changePasswordDTO, BindingResult bindingResult) {
        ValidationHandler.validate(bindingResult);
        return new ResponseHelper<>()
                .setParam("data", userService.changePassword(changePasswordDTO))
                .send();
    }

    @DeleteMapping("/_admin/users/{id}")
    public ResponseEntity delete(@PathVariable("id") int id) {
        userService.delete(id);
        return new ResponseHelper<>()
                .setMessage("Berhasil menghapus user")
                .send();
    }

    @PutMapping("/_admin/users/{id}")
    public ResponseEntity edit(
            @PathVariable("id") int id, @Valid @RequestBody UserDTO userDTO, BindingResult bindingResult) {
        ValidationHandler.validate(bindingResult);
        return new ResponseHelper<>()
                .setParam("data", userService.edit(id, userDTO))
                .send();
    }

    @PutMapping("/users/_profile")
    public ResponseEntity editProfile(@Valid @RequestBody UserDTO userDTO) {
        return new ResponseHelper<>()
                .setParam("data", userService.edit(null, userDTO))
                .send();
    }

    @PutMapping("/users/_profile/_photo")
    public ResponseEntity editPhoto(MultipartFile multipartFile) {
        return new ResponseHelper<>()
                .setParam("data", userService.editPhoto(multipartFile))
                .send();
    }
}
