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
        options.addArguments("--disable-gpu");

        WebDriver driver = new ChromeDriver(options);
        try {
            driver.get("http://103.139.122.250:4000/login");

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

            // Wait for any input field to appear (Next.js takes time to render)
            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("input")));

            // Find all inputs and print them for debugging
            java.util.List<WebElement> inputs = driver.findElements(By.cssSelector("input"));
            System.out.println("Found " + inputs.size() + " input fields");
            for (WebElement input : inputs) {
                System.out.println("Input - type: " + input.getAttribute("type") 
                    + ", name: " + input.getAttribute("name")
                    + ", id: " + input.getAttribute("id")
                    + ", placeholder: " + input.getAttribute("placeholder"));
            }

            // Use the first input for email, second for password
            inputs.get(0).sendKeys("qasim@malik.com");
            inputs.get(1).sendKeys("abcdefg");

            // Click submit button
            WebElement submitBtn = driver.findElement(By.cssSelector("button[type='submit']"));
            submitBtn.click();

            Thread.sleep(3000);

            String pageSource = driver.getPageSource();
            System.out.println("After login page source snippet: " + pageSource.substring(0, 500));

            assertTrue(pageSource.contains("Incorrect") || pageSource.contains("invalid") 
                || pageSource.contains("error") || pageSource.contains("wrong"));

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            driver.quit();
        }
    }
}