package com.midaswebserver.midasweb.services;

import org.springframework.stereotype.Service;

/**
 * @version 0.0.1
 * implements custom implementation of hash used primarily in password hashing
 * TODO: move hashing of passwords to the user service
 * @Author Aidan Scott
 * @since 0.0.1
 */
@Service
public class HashServiceImp implements HashService {
    public HashServiceImp() {
    }

    /**
     * Hashes a password using java hashing (bad)
     *
     * @param rawString raw unhashed password
     * @return hashed password
     */
    @Override
    public String getHash(String rawString) {
        return Integer.toString(rawString.hashCode());
    }
}
