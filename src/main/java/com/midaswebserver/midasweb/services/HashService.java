package com.midaswebserver.midasweb.services;

/**
 * @version 0.0.1
 * implements custom implementation of hash used primarily in password hashing
 * TODO: move hashing of passwords to the user service
 * @Author Aidan Scott
 * @since 0.0.1
 */
public interface HashService {
    /**
     * Hashes a password using custom hashing implementation
     *
     * @param rawString raw unhashed password
     * @return hashed password
     */
    String getHash(String rawString);
}
