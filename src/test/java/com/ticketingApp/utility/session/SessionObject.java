package com.ticketingApp.utility.session;

import java.util.HashMap;
import java.util.Map;

public class SessionObject {
    // Singleton Design
    private static SessionObject instance;
    private Map<String, Object> data = new HashMap<>();

    // Private constructor to prevent instantiation from outside
    private SessionObject() {}

    public static SessionObject getInstance() {
        if (instance == null) {
            instance = new SessionObject();
        }
        return instance;
    }

    public void set(String key, Object value) {
        data.put(key, value);
    }

    public Object get(String key) {
        return data.get(key);
    }
}
