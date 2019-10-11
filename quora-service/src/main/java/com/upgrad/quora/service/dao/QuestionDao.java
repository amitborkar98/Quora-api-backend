package com.upgrad.quora.service.dao;
import org.springframework.stereotype.Repository;
import com.upgrad.quora.service.entity.questionEntity;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class QuestionDao {

    @PersistenceContext
    private EntityManager entityManager;

    public questionEntity create(questionEntity question){
        entityManager.persist(question);
        return question;
    }
}
