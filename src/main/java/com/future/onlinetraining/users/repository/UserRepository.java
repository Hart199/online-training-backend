package com.future.onlinetraining.users.repository;

import com.future.onlinetraining.users.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findByEmail(String email);

    Page<User> findAll(Pageable pageable);
}
