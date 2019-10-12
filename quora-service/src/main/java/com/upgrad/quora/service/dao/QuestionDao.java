package com.upgrad.quora.service.dao;
import com.upgrad.quora.service.entity.userEntity;
import org.springframework.stereotype.Repository;
import com.upgrad.quora.service.entity.questionEntity;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class QuestionDao {

    @PersistenceContext
    private EntityManager entityManager;

    public questionEntity create(questionEntity question){
        entityManager.persist(question);
        return question;
    }

    public List<questionEntity> getQuestions(){
        TypedQuery<questionEntity> query =entityManager.createQuery("SELECT p from questionEntity p", questionEntity.class);
        List<questionEntity> questions = query.getResultList();
         return questions;
    }

    public questionEntity getQuestionById(String uuid){
        try {
            return entityManager.createNamedQuery("questionById", questionEntity.class).setParameter("uuid", uuid).getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

    public void deleteQuestion(questionEntity question){
        entityManager.remove(question);
    }

    public void updateQuestion(questionEntity question){
        entityManager.merge(question);
    }
}
