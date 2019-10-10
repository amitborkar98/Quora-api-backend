package com.upgrad.quora.api.controller;

import com.upgrad.quora.api.model.SignupUserRequest;
import com.upgrad.quora.api.model.SignupUserResponse;
import com.upgrad.quora.service.business.SignupBusinessService;
import com.upgrad.quora.service.exception.AuthenticationFailedException;
import com.upgrad.quora.service.exception.SignOutRestrictedException;
import com.upgrad.quora.service.exception.SignUpRestrictedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import com.upgrad.quora.service.entity.userEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/")
public class SignupController {

    @Autowired
    private SignupBusinessService signupBusinessService;

    @RequestMapping(method = RequestMethod.POST, path = "/user/signup", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<SignupUserResponse> signup(final SignupUserRequest signupUserRequest) throws SignUpRestrictedException {

    final userEntity user = new userEntity();

    user.setUuid(UUID.randomUUID().toString());
    user.setFirstName(signupUserRequest.getFirstName());
    user.setLastName(signupUserRequest.getLastName());
    user.setUsername(signupUserRequest.getUserName());
    user.setEmail(signupUserRequest.getEmailAddress());
    user.setPassword(signupUserRequest.getPassword());
    user.setDob(signupUserRequest.getDob());
    user.setAboutMe(signupUserRequest.getAboutMe());
    user.setContact(signupUserRequest.getContactNumber());
    user.setCountry(signupUserRequest.getCountry());
    user.setRole("nonadmin");
    user.setSalt("1234abc");

    final userEntity createdUserEntity = signupBusinessService.signup(user);
    SignupUserResponse userResponse = new SignupUserResponse().id(createdUserEntity.getUuid()).status("USER SUCCESSFULLY REGISTERED");

    return new ResponseEntity<SignupUserResponse>(userResponse, HttpStatus.CREATED);
    }
}
