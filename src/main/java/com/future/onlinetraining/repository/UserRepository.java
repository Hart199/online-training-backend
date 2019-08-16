package com.future.onlinetraining.repository;

import com.future.onlinetraining.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findByEmail(String email);

    Page<User> findAll(Pageable pageable);

    @Query(
            value = "from User u where (:role is null or u.role.value = :role) " +
                    "and (:name is null or u.fullname like :name%) "
    )
    Page<User> findAllBySearchTerm(
            Pageable pageable,@Param("role") String role, @Param("name") String name);
}
