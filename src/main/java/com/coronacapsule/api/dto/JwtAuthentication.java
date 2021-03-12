package com.coronacapsule.api.dto;

import static com.google.common.base.Preconditions.checkNotNull;

public class JwtAuthentication {

    public final Long userId;

    public final String nickname;

    public JwtAuthentication(Long userId, String nickname) {
        checkNotNull(userId, "userId must be provided.");
        checkNotNull(nickname, "nickname must be provided.");

        this.userId = userId;
        this.nickname = nickname;
    }

}