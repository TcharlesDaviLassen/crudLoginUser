package com.example.UserLoginSpring.usersession;

import com.example.UserLoginSpring.model.User;
import org.springframework.stereotype.Component;


@Component
public class UserSession {
    private User user;
    private boolean loggedIn;

    public UserSession(User user, boolean loggedIn) {
        this.user = user;
        this.loggedIn = loggedIn;
    }

    public UserSession() {
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }
}
