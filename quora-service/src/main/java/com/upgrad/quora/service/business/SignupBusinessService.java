package com.upgrad.quora.service.business;

import com.upgrad.quora.service.exception.AuthenticationFailedException;
import com.upgrad.quora.service.exception.SignOutRestrictedException;
import com.upgrad.quora.service.exception.SignUpRestrictedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.upgrad.quora.service.dao.UserDao;
import com.upgrad.quora.service.entity.userEntity;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SignupBusinessService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private PasswordCryptographyProvider passwordCryptographyProvider;

    @Transactional(propagation = Propagation.REQUIRED)
    public userEntity signup(userEntity user) throws SignUpRestrictedException {

        final userEntity userByEmail = userDao.getUserByEmail(user.getEmail());
        final userEntity userByUsername = userDao.getUserByUsername(user.getUsername());
        //if user with the respective username is already present in the database,AuthenticationFailedException exception is thrown
        if(userByUsername !=null)
        {
            throw new SignUpRestrictedException("SGR-001","Try any other Username,this Username has already been taken.");
        }
        //if user with the respective email is already present in the database,AuthenticationFailedException exception is thrown
        else if(userByEmail != null)
        {
            throw new SignUpRestrictedException("SGR-002","This user has already been registered,try with any other emailId");
        }
        //else the password of the user is encrypted and the user is registered in the database
        else {
            String[] encryptedText = passwordCryptographyProvider.encrypt(user.getPassword());
            user.setSalt(encryptedText[0]);
            user.setPassword(encryptedText[1]);
            return userDao.createUser(user);
        }
    }
}
