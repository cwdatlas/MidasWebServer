package com.midaswebserver.midasweb.services;

import org.springframework.stereotype.Service;

@Service
public class HashServiceImp implements HashService{
    public HashServiceImp(){}
    @Override
    public String getHash(String rawString) {
        return Integer.toString(rawString.hashCode());
    }
}
