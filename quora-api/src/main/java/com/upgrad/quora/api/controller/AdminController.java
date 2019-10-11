package com.upgrad.quora.api.controller;
import com.upgrad.quora.api.model.UserDeleteResponse;
import com.upgrad.quora.api.model.UserDetailsResponse;
import com.upgrad.quora.service.business.UserDeleteBusinessService;
import com.upgrad.quora.service.entity.userEntity;
import com.upgrad.quora.service.exception.AuthorizationFailedException;
import com.upgrad.quora.service.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/")
@RestController
public class AdminController {

    @Autowired
    UserDeleteBusinessService userDeleteBusinessService;

    @RequestMapping(method = RequestMethod.DELETE,path = "admin/user/{userId}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<UserDeleteResponse> delete (@PathVariable("userId") String id ,
                                                      @RequestHeader("authorization")final String authorization)
            throws AuthorizationFailedException, UserNotFoundException {

        userEntity user= userDeleteBusinessService.deleteUser(id, authorization);
        UserDeleteResponse userDeleteResponse=new UserDeleteResponse().id(user.getUuid()).status("USER SUCCESSFULLY DELETED");
        return new ResponseEntity<UserDeleteResponse>(userDeleteResponse,HttpStatus.OK);
    }

}
