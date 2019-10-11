package com.upgrad.quora.service.business;
import com.upgrad.quora.service.exception.AuthenticationFailedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.upgrad.quora.service.dao.UserDao;
import com.upgrad.quora.service.entity.userEntity;
import com.upgrad.quora.service.entity.userAuthEntity;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.UUID;

@Service
public class SigninBusinessService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private PasswordCryptographyProvider passwordCryptographyProvider;

    @Transactional(propagation = Propagation.REQUIRED)
    public userAuthEntity login(final String username,final String password) throws AuthenticationFailedException {

        userEntity user = userDao.getUserByUsername(username);
        //if the username is invalid AuthenticationFailedException is thrown
        if (user == null) {
            throw new AuthenticationFailedException("ATH-001","This username does not exist");
        }
        final String encryptedPassword = passwordCryptographyProvider.encrypt(password,user.getSalt());
        //if password matches then authToken is created for the user and registered in the database
        if(encryptedPassword.equals(user.getPassword())) {

            userAuthEntity userAuthToken = new userAuthEntity();
            JwtTokenProvider jwtTokenProvider=new JwtTokenProvider(encryptedPassword);
            userAuthToken.setUser(user);
            userAuthToken.setUuid(UUID.randomUUID().toString());
            final ZonedDateTime now = ZonedDateTime.now();
            final ZonedDateTime expiresAt = now.plusHours(8);
            userAuthToken.setAccessToken(jwtTokenProvider.generateToken(user.getUuid(),now,expiresAt));
            userAuthToken.setLoginAt(now);
            userAuthToken.setExpiresAt(expiresAt);
            userDao.createAuthToken(userAuthToken);
            return userAuthToken;
        }
        //if password does not match, AuthenticationFailedException is thrown
        else {
            throw new AuthenticationFailedException("ATH-002", "Password failed");
        }
    }
}
