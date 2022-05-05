package com.lecturefeed.selenium.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class LectureFeedStartPage extends LectureFeedBasePage{

    private By surveyTabPanel = By.id("p-tabpanel-1-label");

    public LectureFeedStartPage(ChromeDriver driver)
    {
        super(driver);
    }

    public boolean findSurveyTab()
    {
        try
        {
            chromeDriver.findElement(surveyTabPanel);
        }
        catch(NoSuchElementException e)
        {
            return false;
        }
        return true;
    }
}
