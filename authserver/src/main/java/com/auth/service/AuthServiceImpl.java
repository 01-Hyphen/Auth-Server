package com.auth.service;

import com.auth.dto.UserObjDto;
import com.auth.entity.RoleObj;
import com.auth.entity.UserObj;
import com.auth.exception.EntityAlreadyExistException;
import com.auth.exception.EntityNotFoundException;
import com.auth.mapper.UserObjMapper;
import com.auth.repo.RoleObjRepo;
import com.auth.repo.UserObjRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.HashSet;
import java.util.List;

import java.util.Optional;

@Service
public class AuthServiceImpl {
    @Autowired
    private UserObjRepo userObjRepo;

    @Autowired
    private RoleObjRepo roleObjRepo;

    @Autowired
    PasswordEncoder passwordEncoder;





    public void createUser(UserObjDto userObjDto){
        Optional<UserObj> op = userObjRepo.findByEmail(userObjDto.getEmail());
        if(op.isPresent()){
            throw  new EntityAlreadyExistException("User",userObjDto.getEmail());
        }

        RoleObj roleObj = roleObjRepo.findByRoleName("USER").orElseThrow(()-> new EntityNotFoundException("Role","USER","role"));
        UserObj userObj = UserObjMapper.userDtoToUser(userObjDto,new UserObj());
        String encodedPwd = passwordEncoder.encode(userObjDto.getPassword());
        userObj.setPassword(encodedPwd);
        userObj.getRoles().add(roleObj);
        userObjRepo.save(userObj);
        userObj.setPassword(encodedPwd);
        UserObj userObj1 = userObjRepo.save(userObj);





    }
}
