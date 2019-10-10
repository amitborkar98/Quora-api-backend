package com.upgrad.quora.api.controller;


import com.upgrad.quora.api.model.SigninResponse;
import com.upgrad.quora.service.business.SigninBusinessService;
import com.upgrad.quora.service.business.SignupBusinessService;
import com.upgrad.quora.service.entity.userEntity;
import com.upgrad.quora.service.exception.AuthenticationFailedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import  com.upgrad.quora.service.entity.userAuthEntity;

import java.util.Base64;
import java.util.UUID;

@RestController
@RequestMapping("/")
public class SigninController {

    @Autowired
    SigninBusinessService signinBusinessService;

    @RequestMapping(method = RequestMethod.POST,path = "/user/signin", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<SigninResponse> signin(@RequestHeader("authorization") final String authorization) throws AuthenticationFailedException {

        byte[] decode = Base64.getDecoder().decode(authorization);
        String decodedText = new String(decode);
        String[] decodedArray = decodedText.split(":");

        userAuthEntity token = signinBusinessService.login(decodedArray[0], decodedArray[1]);
        userEntity user = token.getUser();

        SigninResponse signinResponse=new SigninResponse().id((user.getUuid())).message("SIGNED IN SUCCESSFULLY");
        HttpHeaders httpHeaders=new HttpHeaders();
        httpHeaders.add("access-token", token.getAccessToken());
        return new ResponseEntity<SigninResponse>(signinResponse,httpHeaders, HttpStatus.OK);
    }
}
