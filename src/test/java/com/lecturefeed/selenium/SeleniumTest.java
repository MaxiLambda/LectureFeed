package com.lecturefeed.selenium;

import com.lecturefeed.selenium.tests.LFLoginTest;
import org.junit.jupiter.api.*;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.concurrent.TimeUnit;

//@RunWith(SpringRunner.class)
@SpringBootTest
public class SeleniumTest {

        private static ChromeDriver driver;

        @BeforeAll
        public static void setUp() throws Exception {
          WebDriverManager.chromedriver().setup();
          ChromeOptions options = new ChromeOptions().setHeadless(true);
          driver = new ChromeDriver(options);
        }

        @Test
        public void registrationTest() throws Exception {
            driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
            driver.get("http://localhost:8080/#/presenter");
            LFLoginTest lfLoginTest = new LFLoginTest(driver);
            lfLoginTest.registration();
        }

}
