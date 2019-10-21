package com.upgrad.quora.service.business;

import com.upgrad.quora.service.dao.AnswerDao;
import com.upgrad.quora.service.dao.UserDao;
import com.upgrad.quora.service.entity.userAuthEntity;
import com.upgrad.quora.service.entity.userEntity;
import com.upgrad.quora.service.exception.AnswerNotFoundException;
import com.upgrad.quora.service.exception.AuthorizationFailedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.upgrad.quora.service.entity.answerEntity;

import java.time.ZonedDateTime;

@Service
public class AnswerEditBusinessService {

    @Autowired
    UserDao userDao;

    @Autowired
    AnswerDao answerDao;

    @Transactional(propagation = Propagation.REQUIRED)
    public answerEntity update(String authorizationToken,answerEntity editAnswer)throws AuthorizationFailedException,
            AnswerNotFoundException {

        userAuthEntity token = userDao.getUserAuthToken(authorizationToken);
        //if the access token is not there in the database, AuthorizationFailedException is thrown
        if(token == null){
            throw new AuthorizationFailedException("ATHR-001","User has not signed in");
        }
        //if the access token is valid but the user has not logged in, AuthorizationFailedException is thrown
        if(token.getLogoutAt()!= null){
            throw new AuthorizationFailedException("ATHR-002","User is signed out.Sign in first to get user details");
        }
        answerEntity answer = answerDao.getAnswerById(editAnswer.getUuid());
        //if answer Does not exist in the database,AnswerNotFoundException is thrown
        if (answer==null){
            throw new AnswerNotFoundException("ANS-001","Entered answer uuid does not exist");
        }
        userEntity user = token.getUser();
        //if the owner of the answer is logged in user then the answer is updated in the database
        if(answer.getUser() == user)
        {
            answer.setAnswer(editAnswer.getAnswer());
            answer.setDate(ZonedDateTime.now());
            answerDao.updateAnswer(answer);
            return answer;
        }
        //else AuthorizationFailedException is thrown
        throw new AuthorizationFailedException("ATHR-003","Only the answer owner can edit the answer");
    }

}
