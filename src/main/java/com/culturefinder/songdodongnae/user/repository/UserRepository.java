package com.culturefinder.songdodongnae.user.repository;

import com.culturefinder.songdodongnae.user.domain.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
@RequiredArgsConstructor
public class UserRepository {

    @PersistenceContext
    private final EntityManager em;

    public User saveUser(User user) {
        em.persist(user);
        return user;
    }

    public User findUserByEmail(String email) {
        List resultList = em
                .createQuery("select u from User u where u.email = ?1")
                .setParameter(1, email)
                .getResultList();
        return resultList.isEmpty() ? null : (User) resultList.getFirst();
    }
}
