package com.midaswebserver.midasweb.cookies;

import jakarta.servlet.http.Cookie;

public class UserCookie extends Cookie{
    String username;
    /**
     * Constructs a cookie with a specified name and value.
     * <p>
     * The cookie's name cannot be changed after creation.
     * <p>
     * The value can be anything the server chooses to send. Its value is
     * probably of interest only to the server. The cookie's value can be
     * changed after creation with the <code>setValue</code> method.
     *
     * @param name  a <code>String</code> specifying the name of the cookie
     * @param value a <code>String</code> specifying the value of the cookie
     * @throws IllegalArgumentException if the cookie name contains illegal characters
     * @see #setValue
     */
    public UserCookie(String name, String value) {
        super(name, value);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
