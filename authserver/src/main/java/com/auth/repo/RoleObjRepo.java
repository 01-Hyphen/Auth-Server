package com.auth.repo;

import com.auth.entity.RoleObj;
import com.auth.entity.UserObj;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleObjRepo extends JpaRepository<RoleObj,Long> {

    Optional<RoleObj> findByRoleName(String roleName);
}
