package com.auth.repo;

import com.auth.entity.UserObj;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserObjRepo extends JpaRepository<UserObj,Long> {
    Optional<UserObj> findByEmail(String email);
}
