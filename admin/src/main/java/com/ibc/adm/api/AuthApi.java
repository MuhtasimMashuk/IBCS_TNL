package com.ibc.adm.api;

import com.ibc.adm.dto.ChangePasswordDto;
import com.ibc.adm.dto.LoginDto;
import com.ibc.adm.dto.RegistationDto;
import com.ibc.adm.dto.ResponseDto;
import com.ibc.adm.service.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/auth")

public class AuthApi {
    Logger logger = LoggerFactory.getLogger(AuthApi.class);
    @Autowired
    private AuthService authService;

    // consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE}
    @PostMapping(path = "/img")
    public ResponseDto photo( @RequestPart("photo") MultipartFile photo) {
        return authService.photo( photo);
    }

    // consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE}
    @PostMapping(path = "/registration")
    public ResponseDto doRegistration(@RequestBody RegistationDto aa) {
        logger.trace("doRegistration executed from api");
        return authService.registation(aa);
    }

    @PostMapping("/login")
    public ResponseDto doLogin(@RequestBody LoginDto aa) {
        logger.trace("dologin executed from api");
        return authService.login(aa);
    }

    @PostMapping("/changePassword")
    public ResponseDto changePassword(@RequestBody ChangePasswordDto aa) {
        Logger logger = LoggerFactory.getLogger(AuthApi.class);
        return authService.changePassword(aa);
    }
}
