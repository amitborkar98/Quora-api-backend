package com.upgrad.quora.api.controller;

import com.upgrad.quora.api.model.*;
import com.upgrad.quora.service.business.*;
import com.upgrad.quora.service.exception.AuthorizationFailedException;
import com.upgrad.quora.service.exception.InvalidQuestionException;
import com.upgrad.quora.service.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.upgrad.quora.service.entity.questionEntity;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/")
public class QuestionController {

    @Autowired
    QuestionCreateBusinessService questionCreateBusinessService;

    @RequestMapping(method = RequestMethod.POST, path = "/question/create", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<QuestionResponse> createQuestion(final QuestionRequest questionRequest,
                                                           @RequestHeader("authorization") final String authorization)
            throws AuthorizationFailedException {

        final questionEntity question = new questionEntity();
        question.setUuid(UUID.randomUUID().toString());
        question.setContent(questionRequest.getContent());
        question.setDate(ZonedDateTime.now());
        questionEntity questionId = questionCreateBusinessService.create(authorization, question);

        QuestionResponse questionResponse = new QuestionResponse().id(questionId.getUuid()).status("QUESTION CREATED");
        return new ResponseEntity<QuestionResponse>(questionResponse, HttpStatus.CREATED);
    }

    @Autowired
    QuestionGetBusinessService questionGetBusinessService;

    @RequestMapping(method = RequestMethod.GET, path = "/question/all", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<QuestionDetailsResponse> getAllQuestions(@RequestHeader("authorization") final String authorization)
            throws AuthorizationFailedException {

        String[] questions = questionGetBusinessService.getQuestions(authorization);

        QuestionDetailsResponse questionDetailsResponse = new QuestionDetailsResponse().id(questions[0]).content(questions[1]);
        return new ResponseEntity<QuestionDetailsResponse>(questionDetailsResponse, HttpStatus.OK);
    }

    @Autowired
    QuestionDeleteService questionDeleteService;

    @RequestMapping(method = RequestMethod.DELETE,path = "/question/delete/{questionId}",produces =MediaType.APPLICATION_JSON_UTF8_VALUE )
    public ResponseEntity<QuestionDeleteResponse> deleteQuestion(@RequestHeader("authorization") final String authorization,
                                                                     @PathVariable("questionId") String questionId)
            throws AuthorizationFailedException, InvalidQuestionException {

        questionEntity question = questionDeleteService.deleteQuestion(questionId, authorization);

        QuestionDeleteResponse questionDeleteResponse=new QuestionDeleteResponse().id(question.getUuid()).status("QUESTION DELETED");
        return  new ResponseEntity<QuestionDeleteResponse>(questionDeleteResponse,HttpStatus.OK);
    }

    @Autowired
    QuestionEditService questionEditService;

    @RequestMapping(method = RequestMethod.PUT,path = "/question/edit/{questionId}",produces =MediaType.APPLICATION_JSON_UTF8_VALUE )
    public ResponseEntity<QuestionEditResponse> editQuestionContent(final QuestionEditRequest questionEditRequest,
                                                             @RequestHeader("authorization") final String authorization,
                                                             @PathVariable("questionId") String questionId)
            throws AuthorizationFailedException, InvalidQuestionException {

        questionEntity editQuestion = new questionEntity();
        editQuestion.setUuid(questionId);
        editQuestion.setContent(questionEditRequest.getContent());

        questionEntity question = questionEditService.editQuestion(authorization, editQuestion);

        QuestionEditResponse questionEditResponse=new QuestionEditResponse().id(question.getUuid()).status("QUESTION EDITED");
        return new ResponseEntity<QuestionEditResponse>(questionEditResponse,HttpStatus.OK);
    }

    @Autowired
    QuestionByUserBusinessService questionByUserBusinessService;

    @RequestMapping(method = RequestMethod.GET, path = "/question/all/{userId}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<QuestionDetailsResponse> getAllQuestionsByUser(@RequestHeader("authorization") final String authorization,
                                                                     @PathVariable("userId") String userId)
            throws AuthorizationFailedException, UserNotFoundException {

        String[] userQuestions= questionByUserBusinessService.getQuestions(authorization,userId);
        QuestionDetailsResponse questionDetailsResponse = new QuestionDetailsResponse().id(userQuestions[0]).content(userQuestions[1]);
        return new ResponseEntity<QuestionDetailsResponse>(questionDetailsResponse, HttpStatus.OK);
    }
}



