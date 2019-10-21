package com.upgrad.quora.service.business;

import com.upgrad.quora.service.dao.QuestionDao;
import com.upgrad.quora.service.dao.UserDao;
import com.upgrad.quora.service.entity.answerEntity;
import com.upgrad.quora.service.entity.questionEntity;
import com.upgrad.quora.service.entity.userAuthEntity;
import com.upgrad.quora.service.exception.AuthenticationFailedException;
import com.upgrad.quora.service.exception.AuthorizationFailedException;
import com.upgrad.quora.service.exception.InvalidQuestionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AnswerGetAllBusinessService {

    @Autowired
    UserDao userDao;

    @Autowired
    QuestionDao questionDao;

    @Transactional(propagation = Propagation.REQUIRED)
    public List<answerEntity> getAnswers(String authorizationToken, String questionId)throws AuthorizationFailedException, InvalidQuestionException{

        userAuthEntity token = userDao.getUserAuthToken(authorizationToken);
        //if the access token is not there in the database, AuthorizationFailedException is thrown
        if(token == null){
            throw new AuthorizationFailedException("ATHR-001","User has not signed in");
        }
        //if the access token is valid but the user has not logged in, AuthorizationFailedException is thrown
        if(token.getLogoutAt()!= null){
            throw new AuthorizationFailedException("ATHR-002","User is signed out.Sign in first to get user details");
        }
        questionEntity question = questionDao.getQuestionById(questionId);
        //if question does not exist in the database,InvalidQuestionException is thrown
        if(question==null){
            throw new InvalidQuestionException("QUES-001","Entered question uuid does not exist");
        }
        //else all the answers of a question are returned to the controller
        List<answerEntity> answers = question.getAnswers();
        return answers;
    }
}
