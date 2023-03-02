package com.midaswebserver.midasweb.services;

import com.midaswebserver.midasweb.cookies.UserCookie;

public interface CookieService {
     UserCookie cookieToObject(String cookieResult);
    String objectToCookie(UserCookie cookieData);

    UserCookie createCookie(String name, String data);


}
