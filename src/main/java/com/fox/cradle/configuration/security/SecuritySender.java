package com.fox.cradle.configuration.security;


import com.fox.cradle.configuration.security.user.User;
import org.springframework.context.ApplicationEvent;

public class SecuritySender extends ApplicationEvent
{
    private final User user;

    public SecuritySender(Object source, User user) {
        super(source);
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
