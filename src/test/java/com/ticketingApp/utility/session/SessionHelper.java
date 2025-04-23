package com.ticketingApp.utility.session;

public class SessionHelper {

    public static String getSessionObject(String key){
        return (String) SessionObject.getInstance().get(key);
    }
    public static void setSessionObject(String key,Object value){
        SessionObject.getInstance().set(key,value);
    }
}
