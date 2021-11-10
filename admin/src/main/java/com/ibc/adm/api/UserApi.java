package com.ibc.adm.api;

import com.ibc.adm.dto.ResponseDto;
import com.ibc.adm.dto.UserDto;
import com.ibc.adm.service.UserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping( "/userApi")
public class UserApi {
    Logger logger = LoggerFactory.getLogger(UserApi.class);

    @Autowired
    private UserService userService;

    @GetMapping("/")
    Page<UserDto> getAll() {
        logger.trace("get all executed");
        return userService.findAll(PageRequest.of(0, 10), null);
    }

    @GetMapping("/list/")
    List<UserDto> getAllList() {
        logger.trace("get all executed");
        return userService.findAllList();
    }

    @GetMapping("/{id}")
    Object getOne(@PathVariable Long id) {
        logger.trace("get one executed");
        return userService.findById(id);
    }

    @PostMapping("/")
    Object createUser(@RequestBody UserDto newUser) {
        logger.trace("creat user executed");
        return userService.save(newUser);
    }


    @PutMapping("/{id}")
    Object updateUser(@RequestBody UserDto newUser, @PathVariable Long id) {
        logger.trace("update user executed");
        return userService.update(newUser,id);
    }

    @DeleteMapping("/{id}")
    ResponseDto deleteUser(@PathVariable Long id) {
        logger.trace("delete user executed");
        return userService.deleteById(id);
    }

}