package com.upgrad.quora.api.controller;

import com.upgrad.quora.api.model.*;
import com.upgrad.quora.service.business.AnswerCreateBusinessService;
import com.upgrad.quora.service.business.AnswerDeleteBusinessService;
import com.upgrad.quora.service.business.AnswerEditBusinessService;
import com.upgrad.quora.service.business.AnswerGetAllBusinessService;
import com.upgrad.quora.service.exception.AnswerNotFoundException;
import com.upgrad.quora.service.exception.AuthorizationFailedException;
import com.upgrad.quora.service.exception.InvalidQuestionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.upgrad.quora.service.entity.answerEntity;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/")
public class AnswerController {

    @Autowired
    AnswerCreateBusinessService answerCreateBusinessService;

    @RequestMapping(method = RequestMethod.POST, path = "/question/{questionId}/answer/create", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<AnswerResponse> createAnswer(final AnswerRequest answerRequest, @PathVariable("questionId") String questionId,
                                                       @RequestHeader("authorization") final String authorization)
            throws AuthorizationFailedException, InvalidQuestionException {

        final answerEntity answer = new answerEntity();
        answer.setUuid(UUID.randomUUID().toString());
        answer.setDate(ZonedDateTime.now());
        answer.setAnswer(answerRequest.getAnswer());
        answerEntity answerId = answerCreateBusinessService.create(answer, authorization,questionId);

        AnswerResponse answerResponse=new AnswerResponse().id(answerId.getUuid()).status("ANSWER CREATED");
        return  new ResponseEntity<AnswerResponse>(answerResponse,HttpStatus.CREATED);
    }

    @Autowired
    AnswerDeleteBusinessService answerDeleteBusinessService;

    @RequestMapping(method = RequestMethod.DELETE , path ="answer/delete/{answerId}",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<AnswerDeleteResponse> deleteAnswer(@RequestHeader("authorization") final String authorization,
    @PathVariable("answerId") String answerId) throws AuthorizationFailedException, AnswerNotFoundException {

        answerEntity answer = answerDeleteBusinessService.delete(answerId,authorization);

        AnswerDeleteResponse answerDeleteResponse=new AnswerDeleteResponse().id(answer.getUuid()).status("ANSWER DELETED");
        return new ResponseEntity<AnswerDeleteResponse>(answerDeleteResponse,HttpStatus.OK);
    }

    @Autowired
    AnswerEditBusinessService answerEditBusinessService;

    @RequestMapping(method = RequestMethod.PUT,path ="answer/edit/{answerId}",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public  ResponseEntity<AnswerEditResponse> editAnswerContent(@RequestHeader("authorization") final String authorization,
                                                          @PathVariable("answerId")String answerId, final AnswerEditRequest answerEditRequest)
            throws AuthorizationFailedException,AnswerNotFoundException{

        answerEntity answer = new answerEntity();
        answer.setUuid(answerId);
        answer.setAnswer(answerEditRequest.getContent());

        answerEntity updatedAnswer = answerEditBusinessService.update(authorization, answer);

        AnswerEditResponse answerEditResponse=new AnswerEditResponse().id(updatedAnswer.getUuid()).status("ANSWER EDITED");
        return new ResponseEntity<AnswerEditResponse>(answerEditResponse,HttpStatus.OK);
    }

    @Autowired
    AnswerGetAllBusinessService answerGetAllBusinessService;

    @RequestMapping(method = RequestMethod.GET, path = "answer/all/{questionId}",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<AnswerDetailsResponse> getAllAnswersToQuestion(@RequestHeader("authorization") final String authorization,
                                                                         @PathVariable("questionId")String questionId)
            throws AuthorizationFailedException,InvalidQuestionException{

        List<answerEntity> answers = answerGetAllBusinessService.getAnswers(authorization,questionId);
        String content = null;
        String id = null;
        String[] ans = new String[2];
        for(answerEntity q : answers){
            content +=  q.getAnswer() + " , ";
            id +=  q.getUuid() + " , " ;
        }
        ans[0]=id;
        ans[1]=content;

        AnswerDetailsResponse answerDetailsResponse=new AnswerDetailsResponse().id(ans[0]).
                questionContent(answers.get(0).getQuestion().getContent()).answerContent(ans[1]);
        return new ResponseEntity<AnswerDetailsResponse>(answerDetailsResponse,HttpStatus.OK);
    }
}
