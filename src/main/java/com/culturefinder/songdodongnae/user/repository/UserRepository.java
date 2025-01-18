package com.culturefinder.songdodongnae.user.repository;

import com.culturefinder.songdodongnae.user.domain.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
@RequiredArgsConstructor
public class UserRepository {

    @PersistenceContext
    private final EntityManager em;

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
        System.out.println("UserRepository.update");
        User findUser = em.find(User.class, user.getId());
        findUser.update(user.getNickname(), user.getEmail());
        em.persist(findUser);
        return findUser;
    }
}
