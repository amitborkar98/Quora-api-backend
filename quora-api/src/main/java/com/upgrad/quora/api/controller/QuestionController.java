package com.upgrad.quora.api.controller;

import com.upgrad.quora.api.model.QuestionDeleteResponse;
import com.upgrad.quora.api.model.QuestionDetailsResponse;
import com.upgrad.quora.api.model.QuestionRequest;
import com.upgrad.quora.api.model.QuestionResponse;
import com.upgrad.quora.service.business.QuestionCreateBusinessService;
import com.upgrad.quora.service.business.QuestionDeleteService;
import com.upgrad.quora.service.business.QuestionGetBusinessService;
import com.upgrad.quora.service.exception.AuthorizationFailedException;
import com.upgrad.quora.service.exception.InvalidQuestionException;
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
    public ResponseEntity<QuestionDetailsResponse> getQuestions(@RequestHeader("authorization") final String authorization)
            throws AuthorizationFailedException {

        List<questionEntity> questions = questionGetBusinessService.getQuestions(authorization);

        for (questionEntity s : questions) {
            QuestionDetailsResponse questionDetailsResponse = new QuestionDetailsResponse().id(s.getUuid()).content(s.getContent());
            return new ResponseEntity<QuestionDetailsResponse>(questionDetailsResponse, HttpStatus.OK);
        }
        return null;
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
}


