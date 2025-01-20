package com.culturefinder.songdodongnae.user.repository;

import com.culturefinder.songdodongnae.user.domain.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.cors.PreFlightRequestHandler;

import java.util.Optional;

@Repository
@Transactional
@RequiredArgsConstructor
public class UserRepository {

    @PersistenceContext
    private final EntityManager em;
    private final PreFlightRequestHandler preFlightRequestHandler;

    public Optional<User> findById(Long id){
        if(id == null) return Optional.empty();

        return em.createQuery(
                "select u from User u where u.id = :id"
                , User.class)
                .setParameter("id", id)
                .getResultList()
                .stream()
                .findFirst();
    }

    public Optional<User> findByRefreshToken(String refreshToken){
        if(refreshToken == null) return Optional.empty();

        return em.createQuery(
                        "select u from User u where u.refreshToken = :refreshToken"
                        , User.class)
                .setParameter("refreshToken", refreshToken)
                .getResultList()
                .stream()
                .findFirst();
    }

    public Optional<User> findByProviderIdAndProvider(String providerId, String provider) {
        if (providerId == null || provider == null) return Optional.empty();

        return em.createQuery(
                        "select u from User u " +
                                "where u.providerId = :providerId and provider = :provider"
                        , User.class)
                .setParameter("providerId", providerId)
                .setParameter("provider", provider)
                .getResultList()
                .stream()
                .findFirst();
    }

    public User saveUser(User user) {
        em.persist(user);
        return user;
    }

    public User updateUser(User user) {
        User findUser = em.find(User.class, user.getId());
        findUser.update(user.getNickname(), user.getEmail());
        em.persist(findUser);
        return findUser;
    }
}
