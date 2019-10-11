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

    //this method is used to create a user in the database
    public userEntity createUser(userEntity user) {
        entityManager.persist(user);
        return user;
    }

    //this method is used to get a user by email from the database
    public userEntity getUserByEmail(final String email) {
        try {
            return entityManager.createNamedQuery("userByEmail", userEntity.class).setParameter("email", email).getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

    //this method is used to get a user by username from the database
    public userEntity getUserByUsername(final String username) {
        try {
            return entityManager.createNamedQuery("userByUsername", userEntity.class).setParameter("username", username).getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

    //this method is used to get a user by uuid from the database
    public userEntity getUserById(final String uuid){
        try {
        return entityManager.createNamedQuery("userByid", userEntity.class).setParameter("uuid", uuid).getSingleResult();
        }
        catch (NoResultException nre){
            return null;
        }
    }

    //this method is used to delete a user from the database
    public userEntity deleteUser(final String uuid){
        try {
            userEntity user=entityManager.createNamedQuery("userByid", userEntity.class).setParameter("uuid", uuid).getSingleResult();
            entityManager.remove(user);
            return user;
        }
        catch (NoResultException nre){
            return null;
        }
    }

    //this method is used to create a UserAuthToken in the Database
    public userAuthEntity createAuthToken(final userAuthEntity userAuthEntity) {
        entityManager.persist(userAuthEntity);
        return userAuthEntity;
    }

    //this method is used to get a UserAuthToken from the database
    public userAuthEntity getUserAuthToken(final String accesstoken) {
        try {
            return entityManager.createNamedQuery("userAuthByAccessToken", userAuthEntity.class).setParameter("accessToken", accesstoken).getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

}
