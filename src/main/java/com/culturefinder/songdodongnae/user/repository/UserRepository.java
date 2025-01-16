package com.culturefinder.songdodongnae.user.repository;

import com.culturefinder.songdodongnae.user.domain.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepository {

    @PersistenceContext
    private EntityManager em;

    public User findByProviderIdAndProvider(String providerId, String provider){
        return em.createQuery(
                        "select user from User user " +
                                "where user.providerId = :providerId and provider = :provider"
                        , User.class)
                .setParameter("providerId", providerId)
                .setParameter("provider", provider)
                .getResultList()
                .stream()
                .findFirst()
                .orElse(null);
    }

    @Transactional
    public void saveUser(User user){
        em.persist(user);
    }
}
