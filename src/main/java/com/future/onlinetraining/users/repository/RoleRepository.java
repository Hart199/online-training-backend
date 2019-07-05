package com.future.onlinetraining.users.repository;

import com.future.onlinetraining.users.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {

    Role findByValue(String value);

}
