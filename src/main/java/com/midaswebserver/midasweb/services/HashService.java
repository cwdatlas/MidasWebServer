package com.midaswebserver.midasweb.services;
/**
 * @Author Aidan Scott
 * @since 0.0.1
 * @version 0.0.1
 * implements custom implementation of hash used primarily in password hashing
 */
public interface HashService {
    /**
     * @Author Aidan Scott
     * @since 0.0.1
     * Hashes a password using custom hashing implementation
     * @param rawString raw unhashed password
     * @return hashed password
     */
    String getHash(String rawString);
}
