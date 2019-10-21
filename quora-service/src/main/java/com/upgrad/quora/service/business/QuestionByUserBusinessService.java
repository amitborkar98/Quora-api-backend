package com.upgrad.quora.service.business;

import com.upgrad.quora.service.dao.UserDao;
import com.upgrad.quora.service.entity.questionEntity;
import com.upgrad.quora.service.entity.userAuthEntity;
import com.upgrad.quora.service.exception.AuthorizationFailedException;
import com.upgrad.quora.service.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.upgrad.quora.service.entity.userEntity;

import java.util.List;

@Service
public class QuestionByUserBusinessService {

    @Autowired
    UserDao userDao;

    @Transactional(propagation = Propagation.REQUIRED)
    public String[] getQuestions(String authorizationToken,String userId)throws AuthorizationFailedException, UserNotFoundException{

        userAuthEntity token = userDao.getUserAuthToken(authorizationToken);
        //if the access token is not there in the database, AuthorizationFailedException is thrown
        if(token == null){
            throw new AuthorizationFailedException("ATHR-001","User has not signed in");
        }
        //if the access token is valid but the user has not logged in, AuthorizationFailedException is thrown
        if(token.getLogoutAt()!= null){
            throw new AuthorizationFailedException("ATHR-002","User is signed out.Sign in first to get user details");
        }
        //if the user is not present in the database UserNotFound Exception is thrown
        userEntity user = userDao.getUserById(userId);
        if(user == null){
            throw new UserNotFoundException("USR-001","User with entered uuid whose question details are to be seen does not exist");
        }
        //else the questions asked by a specific user are returned to the controller
         List<questionEntity> questions = user.getQuestions();
        String content = " ";
        String id = " ";
        String[] question = new String[2];
        for(questionEntity q : questions){
            content +=  q.getContent() + " , ";
            id +=  q.getUuid() + " , " ;
        }
        question[0]=id;
        question[1]=content;
        return question;
    }
}
