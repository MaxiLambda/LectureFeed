package com.lecturefeed.selenium.tests;

import com.lecturefeed.selenium.pages.LectureFeedRegistrationPage;

public class LFLoginTest {

    public void registration()
    {
        LectureFeedRegistrationPage registrationPage = new LectureFeedRegistrationPage();
        registrationPage.navigateTo("http://localhost:4200/#/presenter");
    }
}
