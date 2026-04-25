package com.example.jenkins_test;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;
import static org.junit.jupiter.api.Assertions.*;

public class LoginTest {

    @Test
    void test_login_with_incorrect_credentials() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");

        WebDriver driver = new ChromeDriver(options);
        try {
            driver.get("http://103.139.122.250:4000/login");

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("input")));

            System.out.println("PAGE SOURCE: " + driver.getPageSource().substring(0, 1000));

            driver.findElement(By.name("email")).sendKeys("qasim@malik.com");
            driver.findElement(By.name("password")).sendKeys("abcdefg");
            driver.findElement(By.id("m_login_signin_submit")).click();

            wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("/html/body/div/div/div[1]/div/div/div/div[2]/form/div[1]")
            ));

            String errorText = driver.findElement(
                By.xpath("/html/body/div/div/div[1]/div/div/div/div[2]/form/div[1]")
            ).getText();

            assertTrue(errorText.contains("Incorrect email or password"));
        } finally {
            driver.quit();
        }
    }
}