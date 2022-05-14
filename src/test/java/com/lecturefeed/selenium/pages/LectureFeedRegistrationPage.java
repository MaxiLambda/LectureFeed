package com.lecturefeed.selenium.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;

public class LectureFeedRegistrationPage extends LectureFeedBasePage {

    public LectureFeedRegistrationPage(ChromeDriver driver)
    {
        super(driver);
    }

    private By createSessionButton = By.xpath("//*[@id=\"pr_id_6\"]/div[1]/div/button");
    private By sessionNameField = By.xpath("/html/body/app-root/app-presenter-management/app-content/div/app-presenter-create-session-dialog/p-dialog/div/div/div[2]/div/div/input");
    private By startSessionButton = By.xpath("/html/body/app-root/app-presenter-management/app-content/div/app-presenter-create-session-dialog/p-dialog/div/div/div[3]/button/span");

    public void clickCreateSessionButton()
    {
        chromeDriver.findElement(createSessionButton).click();
    }

    public void putSessionName(String name)
    {
        chromeDriver.findElement(sessionNameField).sendKeys(name);
    }

    public void clickStartSessionButton()
    {
        chromeDriver.findElement(startSessionButton).click();
    }

}
