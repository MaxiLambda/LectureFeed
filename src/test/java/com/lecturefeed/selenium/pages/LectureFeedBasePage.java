package com.lecturefeed.selenium.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

public class LectureFeedBasePage {
    protected WebDriver chromeDriver = new ChromeDriver();

    public void navigateTo(String url)
    {
        System.out.println(url);
        chromeDriver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        chromeDriver.navigate().to(url);
    }
}
