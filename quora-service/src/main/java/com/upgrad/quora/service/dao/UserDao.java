package com.upgrad.quora.service.dao;


import org.springframework.stereotype.Repository;
import com.upgrad.quora.service.entity.userEntity;
import com.upgrad.quora.service.entity.userAuthEntity;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

@Repository
public class UserDao {

    @PersistenceContext
    private EntityManager entityManager;

    public userEntity createUser(userEntity user) {
        entityManager.persist(user);
        return user;
    }


    public userEntity getUserByEmail(final String email) {
        try {
            return entityManager.createNamedQuery("userByEmail", userEntity.class).setParameter("email", email).getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

    public userEntity getUserByUsername(final String username) {
        try {
            return entityManager.createNamedQuery("userByUsername", userEntity.class).setParameter("username", username).getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

    public userEntity getUserById(final String uuid){
        try {
        return entityManager.createNamedQuery("userByid", userEntity.class).setParameter("uuid", uuid).getSingleResult();
        }
        catch (NoResultException nre){
            return null;
        }
    }


    public userAuthEntity createAuthToken(final userAuthEntity userAuthEntity) {
        entityManager.persist(userAuthEntity);
        return userAuthEntity;
    }

    public userAuthEntity getUserAuthToken(final String accesstoken) {
        try {
            return entityManager.createNamedQuery("userAuthByAccessToken", userAuthEntity.class).setParameter("accessToken", accesstoken).getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

}
