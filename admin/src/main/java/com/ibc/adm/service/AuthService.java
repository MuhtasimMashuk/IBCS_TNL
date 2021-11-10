package com.ibc.adm.service;

import com.ibc.adm.dto.*;
import com.ibc.adm.entity.User;
import com.ibc.adm.repo.UserRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class AuthService {

    Logger logger = LoggerFactory.getLogger(AuthService.class);
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public ResponseDto photo(MultipartFile photo) {


        String sss = null;

        if (photo.getSize() > 0) {

            String fileName = photo.getOriginalFilename();

            String extension = "";

            int i = fileName.lastIndexOf('.');
            if (i > 0) {
                extension = fileName.substring(i + 1);
            }

            sss = UUID.randomUUID().toString() + extension;

            try {
                Files.write(Paths.get("images", "user", "photo", sss), photo.getBytes());
            } catch (Exception e) {
                System.err.println(e);
            }
        }
        return null;
    }

    public ResponseDto registation(RegistationDto registationDto) {
        logger.trace("registration executed");
        if (registationDto.getPassword().equals(registationDto.getMatchPassword())) {

            User userSaved = userRepo.loadSingleUser(registationDto.getCode(), registationDto.getEmail(), registationDto.getMobileNo());

            if (userSaved == null) {
                User user = new User();
                BeanUtils.copyProperties(registationDto, user, "password", "matchPassword");

                // user.setPassword(passwordEncoder.encode(registationDto.getPassword()));
                user.setPassword(registationDto.getPassword());

                if (userRepo.save(user) != null) {
                    return new ResponseDto(ResponseDto.ResponseStatus.SUCCESS, "logged in", user.getId());
                } else
                    return new ResponseDto(ResponseDto.ResponseStatus.ERROR, "Db save err", registationDto.getCode());

            } else
                return new ResponseDto(ResponseDto.ResponseStatus.ERROR, "User exist", registationDto.getCode());

        } else
            return new ResponseDto(ResponseDto.ResponseStatus.ERROR, "Password not match", registationDto.getCode());


    }

    public ResponseDto login(LoginDto loginDto) {
        logger.trace("login executed");
        User user = userRepo.loadSingleUser(loginDto.getLoginId());                        //getById(loginDto.getCode());
        if (user == null) {
            return new ResponseDto(ResponseDto.ResponseStatus.ERROR, "UserNotFound", loginDto.getLoginId());
        } else {

            if (user.isActive()) {
                // if (passwordEncoder.matches(loginDto.getPassword(), user.getPassword())) {
                if (loginDto.getPassword().equals(user.getPassword())) {
                    //id,photo,name
                    return new ResponseDto(ResponseDto.ResponseStatus.SUCCESS, "logged in", user.getId());
                } else {
                    return new ResponseDto(ResponseDto.ResponseStatus.ERROR, "Wrong credential", loginDto.getLoginId());
                }
            } else {
                return new ResponseDto(ResponseDto.ResponseStatus.ERROR, "User Inactive", loginDto.getLoginId());
            }

        }
    }

    public ResponseDto changePassword(ChangePasswordDto changePasswordDto) {
        logger.trace("chngePassword executed");
        User user = userRepo.getById(changePasswordDto.getUserId());

        if (changePasswordDto.getOldPassword().equals( user.getPassword())) {
//        if (passwordEncoder.matches(changePasswordDto.getOldPassword(), user.getPassword())) {

            if (user != null && changePasswordDto.getNewPassword().equals(changePasswordDto.getRetypePassword())) {

                user.setPassword(changePasswordDto.getNewPassword());
                userRepo.save(user);
//                user.setPassword(passwordEncoder.encode(changePasswordDto.getNewPassword()));
                return new ResponseDto(ResponseDto.ResponseStatus.UPDATED, "password changed", user.getId());
            } else {
                return new ResponseDto(ResponseDto.ResponseStatus.ERROR, "error happened", user.getId());
            }
        } else {
            return new ResponseDto(ResponseDto.ResponseStatus.ERROR, "old password mismatch", user.getId());
        }
    }

}
