package com.lecturefeed.selenium.pages;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

public class LectureFeedBasePage {
    protected static WebDriver chromeDriver;

    public LectureFeedBasePage(ChromeDriver driver)
    {
        chromeDriver = driver;
    }
}
