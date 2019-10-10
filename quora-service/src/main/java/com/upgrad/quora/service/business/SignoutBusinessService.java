package com.upgrad.quora.service.business;

import com.upgrad.quora.service.dao.UserDao;
import com.upgrad.quora.service.entity.userAuthEntity;
import com.upgrad.quora.service.exception.SignOutRestrictedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;

@Service
public class SignoutBusinessService {

    @Autowired
    private UserDao userDao;

    @Transactional(propagation = Propagation.REQUIRED)
    public userAuthEntity signout(final String authorizationToken) throws SignOutRestrictedException {

       userAuthEntity token = userDao.getUserAuthToken(authorizationToken);
       if (token == null){
           throw new SignOutRestrictedException("SGR-001","User is not Signed in");
       }
       else
           token.setLogoutAt(ZonedDateTime.now());

       return token;
    }
}
