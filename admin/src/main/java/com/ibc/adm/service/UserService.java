package com.ibc.adm.service;


import com.ibc.adm.dto.ResponseDto;
import com.ibc.adm.dto.UserDto;
import com.ibc.adm.entity.Role;
import com.ibc.adm.entity.User;
import com.ibc.adm.repo.UserRepo;
import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    Logger logger = LoggerFactory.getLogger(UserService.class);
    @Autowired
    private UserRepo userRepo;


    private UserDto conv(User user) {
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(user, userDto);
        userDto.setRoles(String.valueOf(user.getRoles()));
        return userDto;
    }

    public Page<UserDto> findAll(Pageable pageable, String sText) {
        Page<User> user = userRepo.findAllCustom(pageable, sText);


        List<UserDto> sss = new ArrayList(pageable.getPageSize());
        for (User pp : user.getContent()) {
            sss.add(conv(pp));
        }

        Page<UserDto> userDtos = new PageImpl(sss, pageable, user.getTotalElements());

        return userDtos;
    }

    public List<UserDto> findAllList() {
        List<User> user = userRepo.findAll();


        List<UserDto> sss = new ArrayList();
        for (User pp : user) {
            sss.add(conv(pp));
        }

        return sss;
    }

    public Object findById(Long id) {
        logger.trace("find by id executed");
        UserDto userDto = new UserDto();

        if(!userRepo.existsById(id)){
            logger.trace("id not found");

           // userDto.setResponseDto(responseDto);
            return new ResponseDto(ResponseDto.ResponseStatus.ERROR, "User not found",id);
        }
        User user = userRepo.getById(id);
        BeanUtils.copyProperties(user, userDto,"roles");
        userDto.setRoles(String.valueOf(user.getRoles()));

        //userDto.setResponseDto(responseDto);

       // return new ResponseDto(ResponseDto.ResponseStatus.SUCCESS, "User retrieved",userDto);
        return userDto;
    }


    public Object save(UserDto userDto) {
        User user = new User();
        BeanUtils.copyProperties(userDto, user,"roles");
        user.setRoles(Role.valueOf(userDto.getRoles()));

       try{
           userDto=conv(userRepo.save(user));
           System.out.println(user);
          return new ResponseDto(ResponseDto.ResponseStatus.SUCCESS, "user created", userDto);
       }
       catch (Exception e){

           //userDto.setResponseDto(responseDto);
           return  new ResponseDto(ResponseDto.ResponseStatus.ERROR, "user creation is not possible", e);
       }

    }


    public Object update(UserDto userDto, Long id) {

        logger.trace(" update executed from service");
        if(!userRepo.existsById(id)) {
            logger.trace(" id not found");

          // userDto.setResponseDto(responseDto);
            return new ResponseDto(ResponseDto.ResponseStatus.ERROR, "Update is not possible",id);
        }
        User user = userRepo.getById(id);
        BeanUtils.copyProperties(userDto, user,"id","responseDto");
        user.setId(id);
        user.setRoles(Role.valueOf(userDto.getRoles()));

        user=userRepo.save(user);

        BeanUtils.copyProperties(user, userDto,"responseDto");
        userDto.setRoles(String.valueOf(user.getRoles()));

       // userDto.setResponseDto(responseDto);

        return new ResponseDto(ResponseDto.ResponseStatus.SUCCESS, "Update done",userDto);
    }







    public ResponseDto deleteById(Long id) {
        logger.trace(" deleteById executed from service");

        if(!userRepo.existsById(id)) {
            logger.trace(" id is not found");
            return new ResponseDto(ResponseDto.ResponseStatus.ERROR, "Delete is not possible",id);
        }

        return new ResponseDto(ResponseDto.ResponseStatus.SUCCESS, "Deletion complete",id);
    }
}
