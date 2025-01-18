package com.culturefinder.songdodongnae.user.domain;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;

public enum OAuthAttributes {
    NAVER("naver", (attributes) -> {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");
        return new UserProfile(
                (String) response.get("id"),
                (String) response.get("name"),
                (String) response.get("email"),
                "naver"
        );
    }),
    GOOGLE("google", (attributes) -> new UserProfile(
            (String) attributes.get("sub"),
            (String) attributes.get("name"),
            (String) attributes.get("email"),
            "google"
    )),
    KAKAO("kakao", (attributes) -> {
        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
        if (kakaoAccount == null) {
            throw new IllegalStateException("Kakao account error.");
        }
        Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");
        if (profile == null) {
            throw new IllegalStateException("Profile error.");
        }
        return new UserProfile(
                String.valueOf(attributes.get("id")),
                (String) profile.get("nickname"),
                (String) kakaoAccount.get("email"),
                "kakao"
        );
    });

    private final String registrationId;
    private final Function<Map<String, Object>, UserProfile> of;

    OAuthAttributes(String registrationId, Function<Map<String, Object>, UserProfile> of) {
        this.registrationId = registrationId;
        this.of = of;
    }

    public static UserProfile extract(String registrationId, Map<String, Object> attributes) {
        return Arrays.stream(values())
                .filter(provider -> registrationId.equals(provider.registrationId))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new)
                .of.apply(attributes);
    }
}
