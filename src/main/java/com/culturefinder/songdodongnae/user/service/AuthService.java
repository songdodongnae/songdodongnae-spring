package com.culturefinder.songdodongnae.user.service;

import org.springframework.stereotype.Component;

@Component
public class AuthService {
    public Long getAuthenticatedUserId() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        boolean isAuthenticatedUser = authentication != null &&
//                authentication.isAuthenticated() &&
//                !(authentication.getPrincipal() instanceof String &&
//                        authentication.getPrincipal().equals("anonymousUser"));
//        if(!isAuthenticatedUser) throw new CustomException(ErrorCode.FORBIDDEN);
//        return Long.parseLong(authentication.getName());
        return 3L;
    }

    public boolean isAuthenticatedUser() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        return authentication != null &&
//                authentication.isAuthenticated() &&
//                !(authentication.getPrincipal() instanceof String &&
//                        authentication.getPrincipal().equals("anonymousUser"));

        return true;
    }
}
