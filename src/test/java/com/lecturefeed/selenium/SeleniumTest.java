package com.lecturefeed.selenium;

import com.lecturefeed.selenium.tests.LFLoginTest;
import org.junit.Before;
import org.junit.Test;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

//@RunWith(SpringRunner.class)
@SpringBootTest
public class SeleniumTest {

        @LocalServerPort
        private int port= 4200;
        private ChromeDriver driver;

        @Value("${server.contextPath}")
        private String contextPath;
        private String base;

        @Before
        public void setUp() throws Exception {
          WebDriverManager.chromedriver().setup();
          driver = new ChromeDriver();
          this.base = "http://localhost:" + port;
        }

        @Test
        public void registrationTest() throws Exception {
            //driver.get("http://localhost:4200/#/presenter/");
            LFLoginTest lfLoginTest = new LFLoginTest(driver);
            lfLoginTest.registration();
        }
}
