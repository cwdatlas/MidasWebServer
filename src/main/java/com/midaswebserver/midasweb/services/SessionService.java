package com.midaswebserver.midasweb.services;

import com.midaswebserver.midasweb.sessions.UserSession;

public interface SessionService {
     UserSession cookieToObject(String cookieResult);
    String objectToCookie(UserSession cookieData);

    UserSession createCookie(String name, String data);


}
