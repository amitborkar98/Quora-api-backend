package com.upgrad.quora.service.business;
import com.upgrad.quora.service.dao.AnswerDao;
import com.upgrad.quora.service.dao.UserDao;
import com.upgrad.quora.service.entity.answerEntity;
import com.upgrad.quora.service.entity.userAuthEntity;
import com.upgrad.quora.service.entity.userEntity;
import com.upgrad.quora.service.exception.AnswerNotFoundException;
import com.upgrad.quora.service.exception.AuthorizationFailedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AnswerDeleteBusinessService {

    @Autowired
    UserDao userDao;

    @Autowired
    AnswerDao answerDao;

    @Transactional(propagation = Propagation.REQUIRED)
    public answerEntity delete(String id,String authorizationToken )throws AuthorizationFailedException, AnswerNotFoundException {

        userAuthEntity token = userDao.getUserAuthToken(authorizationToken);
        //if the access token is not there in the database, AuthorizationFailedException is thrown
        if(token == null){
            throw new AuthorizationFailedException("ATHR-001","User has not signed in");
        }
        //if the access token is valid but the user has not logged in, AuthorizationFailedException is thrown
        if(token.getLogoutAt()!= null){
            throw new AuthorizationFailedException("ATHR-002","User is signed out.Sign in first to get user details");
        }
        userEntity user =token.getUser();

        answerEntity answer = answerDao.getAnswerById(id);
        //if answer Does not exist in the database,AnswerNotFoundException is thrown
        if (answer==null){
            throw new AnswerNotFoundException("ANS-001","Entered answer uuid does not exist");
        }
        //if the user of the answer is the logged in user or the role of the user is admin the question is deleted from the database
        if(answer.getUser() == user || user.getRole().equals("admin")){
            answerDao.deleteAnswer(answer);
            return answer;
        }
        //else AuthorizationFailedException is thrown
        throw new AuthorizationFailedException("ATHR-003","Only the answer owner or admin can delete the answer");
    }
}
