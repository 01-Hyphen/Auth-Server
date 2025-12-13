package com.auth.mapper;

import com.auth.dto.UserObjDto;
import com.auth.entity.UserObj;

public class UserObjMapper {

    public static UserObj userDtoToUser(UserObjDto userObjDto,UserObj userObj){
        userObj.setEmail(userObjDto.getEmail());
        userObj.setLastName(userObjDto.getLastName());
        userObj.setFirstName(userObjDto.getFirstName());
        return userObj;
    }

}
