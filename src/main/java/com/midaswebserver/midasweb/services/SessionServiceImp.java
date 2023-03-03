package com.midaswebserver.midasweb.services;

import com.midaswebserver.midasweb.sessions.UserSession;

public class SessionServiceImp implements SessionService {
    @Override
    public UserSession cookieToObject(String cookieResult) {
        return null;
    }

    @Override
    public String objectToCookie(UserSession cookieData) {
        return null;
    }

    @Override
    public UserSession createCookie(String name, String data) {
        return null;
    }
}
