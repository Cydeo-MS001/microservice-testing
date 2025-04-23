package com.ticketingApp.utility.constants;

import com.ticketingApp.utility.config.ConfigurationReader;

public class EndpointConstants {
    public final static String PROJECT_BASEPATH = ConfigurationReader.getProperty("project");
    public final static String USER_BASEPATH = ConfigurationReader.getProperty("user");
    public final static String TASK_BASEPATH = ConfigurationReader.getProperty("task");
    public final static String TOKEN_URL = ConfigurationReader.getProperty("token.url");

    public final static String CREATE_PROJECT = "/create";
    public final static String CREATE_TASK = "/create";
    public final static String READ_ALL_PROJECTS = "/read/all/manager";
    public final static String DELETE_PROJECT_BY_PROJECT_CODE = "/delete/{projectCode}";

}
