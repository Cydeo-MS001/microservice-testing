package com.ticketingApp.steps;

import com.ticketingApp.utility.db.DB_Util;
import com.ticketingApp.utility.session.SessionHelper;
import io.cucumber.java.en.And;
import org.junit.Assert;

public class DBStepDefs {

    @And("Verify all task in database for same {string} is {string}")
    public void verifyAllTaskInDatabaseForSameIs(String sessionKey, String expectedValue) {

        String projectCode = SessionHelper.getSessionObject(sessionKey);

        System.out.println("projectCode = " + projectCode);

        DB_Util.createConnection("task.service");

        String query="SELECT TASK_STATUS FROM TASKS WHERE PROJECT_CODE='"+projectCode+"'";

        DB_Util.runQuery(query);

        String actualStatus = DB_Util.getFirstRowFirstColumn();

        System.out.println("actualStatus = " + actualStatus);

        Assert.assertEquals(expectedValue,actualStatus);
    }
}
