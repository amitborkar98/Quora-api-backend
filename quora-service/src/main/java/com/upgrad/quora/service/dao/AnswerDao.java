package com.upgrad.quora.service.dao;
import org.springframework.stereotype.Repository;
import com.upgrad.quora.service.entity.answerEntity;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class AnswerDao {

    @PersistenceContext
    private EntityManager entityManager;

    //this method is used to create a Answer
    public answerEntity create(answerEntity answer){
        entityManager.persist(answer);
        return answer;
    }

    //this method is used to get the Answer by uuid
    public answerEntity getQuestionById(String uuid){
        try {
            return entityManager.createNamedQuery("answerById", answerEntity.class).setParameter("uuid", uuid).getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

    //this method is used to delete the answer
    public void deleteQuestion(answerEntity answer){
        entityManager.remove(answer);
    }

    //this method is used to update the answer
    public void updateQuestion(answerEntity answer){
        entityManager.merge(answer);
    }


}
