package com.upgrad.quora.api.controller;

import com.upgrad.quora.api.model.AnswerDeleteResponse;
import com.upgrad.quora.api.model.AnswerRequest;
import com.upgrad.quora.api.model.AnswerResponse;
import com.upgrad.quora.service.business.AnswerCreateBusinessService;
import com.upgrad.quora.service.business.AnswerDeleteBusinessService;
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
}
