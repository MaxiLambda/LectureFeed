package com.lecturefeed.selenium;

import com.lecturefeed.selenium.tests.LFLoginTest;
import org.junit.jupiter.api.*;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.concurrent.TimeUnit;

//@RunWith(SpringRunner.class)
@SpringBootTest
public class SeleniumTest {

        //@LocalServerPort
        //private int port= 8080;
        private static ChromeDriver driver;

        //@Value("${server.contextPath}")
        //private String contextPath;
        //private String base;

        //private static Process p = null;

        @BeforeAll
        public static void setUp() throws Exception {
          WebDriverManager.chromedriver().setup();
          driver = new ChromeDriver();
          //ProcessBuilder pb = new ProcessBuilder("./start_jar.bat");
          //try
          //{
          //    p = pb.start();
          //}
          //catch(Exception e)
          //{
          //    e.printStackTrace();
          //}
          //this.base = "http://localhost:" + port;

           // driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        }

        //@AfterAll
        //public static void stopProcess()
        //{
        //    p.destroy();
        //}

        @Test
        public void registrationTest() throws Exception {
            //driver.get("http://localhost:4200/#/presenter/");
            LFLoginTest lfLoginTest = new LFLoginTest(driver);
            lfLoginTest.registration();
        }
}
