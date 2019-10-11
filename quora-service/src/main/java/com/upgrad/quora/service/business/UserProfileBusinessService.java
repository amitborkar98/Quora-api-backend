package com.upgrad.quora.service.business;

import com.upgrad.quora.service.dao.UserDao;
import com.upgrad.quora.service.entity.userAuthEntity;
import com.upgrad.quora.service.entity.userEntity;
import com.upgrad.quora.service.exception.AuthenticationFailedException;
import com.upgrad.quora.service.exception.AuthorizationFailedException;
import com.upgrad.quora.service.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


@Service
public class UserProfileBusinessService {

    @Autowired
    UserDao userDao;

    @Transactional(propagation = Propagation.REQUIRED)
    public userEntity details(String id,String authorizationToken) throws UserNotFoundException, AuthorizationFailedException {

        userAuthEntity token = userDao.getUserAuthToken(authorizationToken);
        if(token == null){
            throw new AuthorizationFailedException("ATHR-001","User has not signed in");
        }
        if(token.getLogoutAt()!= null){
            throw new AuthorizationFailedException("ATHR-002","User is signed out.Sign in first to get user details");
        }
        userEntity userById = userDao.getUserById(id);
        if(userById == null){
            throw new UserNotFoundException("USR-001","User with entered uuid does not exist");
        }

        return userById;
    }

}


