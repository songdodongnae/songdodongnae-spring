package com.culturefinder.songdodongnae.admin;

import org.springframework.stereotype.Component;

import java.util.HashSet;

@Component
public class AdminSessionManager {

    private final HashSet<String> storage = new HashSet<>();

    public void store(String session) {
        storage.add(session);
    }

    public boolean contains(String session) {
        return storage.contains(session);
    }

}
