package com.lecturefeed.selenium.tests;

import com.lecturefeed.selenium.pages.LectureFeedRegistrationPage;
import com.lecturefeed.selenium.pages.LectureFeedStartPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class LFLoginTest {

    LectureFeedRegistrationPage registrationPage;
    LectureFeedStartPage startPage;

    public LFLoginTest(ChromeDriver driver)
    {
        registrationPage = new LectureFeedRegistrationPage(driver);
        startPage = new LectureFeedStartPage(driver);
    }

    public void registration()
    {
        registrationPage.navigateTo("http://localhost:4200/#/presenter");
        registrationPage.clickCreateSessionButton();
        registrationPage.putSessionName("test");
        registrationPage.clickStartSessionButton();
        assert startPage.findSurveyTab() == true;
    }
}
