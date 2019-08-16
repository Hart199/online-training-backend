package com.future.onlinetraining.repository;

import com.future.onlinetraining.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {

    Role findByValue(String value);

}
