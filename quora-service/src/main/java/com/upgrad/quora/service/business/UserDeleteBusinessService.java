package com.upgrad.quora.service.business;

import com.upgrad.quora.service.dao.UserDao;
import com.upgrad.quora.service.entity.userAuthEntity;
import com.upgrad.quora.service.exception.AuthorizationFailedException;
import com.upgrad.quora.service.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.upgrad.quora.service.entity.userEntity;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDeleteBusinessService {

    @Autowired
    UserDao userDao;

    @Transactional(propagation = Propagation.REQUIRED)
    public userEntity deleteUser(String id,String authorizationToken)throws  AuthorizationFailedException, UserNotFoundException {

        userAuthEntity token= userDao.getUserAuthToken(authorizationToken);
        //if the access token is not there in the database, AuthorizationFailedException is thrown
        if(token == null){
            throw new AuthorizationFailedException("ATHR-001","User has not signed in");
        }
        //if the access token is valid but the user has not logged in, AuthorizationFailedException is thrown
        if(token.getLogoutAt()!= null){
            throw new AuthorizationFailedException("ATHR-002","User is signed out.Sign in first to get user details");
        }
        userEntity user = token.getUser();
        //if the role of the user who is trying to delete is non-admin, AuthorizationFailedException is thrown
        if(user.getRole().equals("nonadmin")) {
            throw new AuthorizationFailedException("ATHR-003","Unauthorized Access,Entered user is not an admin");
        }
        userEntity deletedUser=userDao.deleteUser(id);
        //if the user to be deleted is not present in the database, UserNotFoundException is thrown
        if(deletedUser==null) {
            throw new UserNotFoundException("USR-001","User with entered uuid to be deleted does not exist");
        }
        return  deletedUser;
    }
}
