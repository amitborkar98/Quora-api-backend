package com.upgrad.quora.service.business;

import com.upgrad.quora.service.dao.AnswerDao;
import com.upgrad.quora.service.dao.QuestionDao;
import com.upgrad.quora.service.dao.UserDao;
import com.upgrad.quora.service.entity.questionEntity;
import com.upgrad.quora.service.entity.userAuthEntity;
import com.upgrad.quora.service.exception.AuthorizationFailedException;
import com.upgrad.quora.service.exception.InvalidQuestionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.upgrad.quora.service.entity.answerEntity;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.upgrad.quora.service.entity.userEntity;

import java.util.List;

@Service
public class AnswerCreateBusinessService {

    @Autowired
    UserDao userDao;

    @Autowired
    QuestionDao questionDao;

    @Autowired
    AnswerDao answerDao;

    @Transactional(propagation = Propagation.REQUIRED)
    public answerEntity create(answerEntity answer,String authorizationToken,String questionId) throws AuthorizationFailedException,
            InvalidQuestionException{

        questionEntity question= questionDao.getQuestionById(questionId);
        if(question==null){
            throw new InvalidQuestionException("QUES-001","The question entered is invalid");
        }
        userAuthEntity token = userDao.getUserAuthToken(authorizationToken);
        //if the access token is not there in the database, AuthorizationFailedException is thrown
        if(token == null){
            throw new AuthorizationFailedException("ATHR-001","User has not signed in");
        }
        //if the access token is valid but the user has not logged in, AuthorizationFailedException is thrown
        if(token.getLogoutAt()!= null){
            throw new AuthorizationFailedException("ATHR-002","User is signed out.Sign in first to get user details");
        }
        //else the user and the question of the answer is set and saved in the database
        answer.setQuestion(question);
        userEntity user = token.getUser();
        answer.setUser(user);
        answerEntity answerId = answerDao.create(answer);

        return answerId;
    }

}
