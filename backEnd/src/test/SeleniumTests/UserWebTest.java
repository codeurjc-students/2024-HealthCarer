package SeleniumTests;

import EvaRuiz.HealthCarer.HealthCarerApplication;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.time.Duration;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes= HealthCarerApplication.class)
public class UserWebTest {
    @LocalServerPort
    int port;

    protected WebDriver driver;
    protected WebDriverWait wait;

    @BeforeEach
    public void setUpTest() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--allow-insecure-localhost");
        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }
    @AfterEach
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void login() {
        driver.get("https://localhost:"+this.port+"/");
        driver.findElement(By.id("username")).sendKeys("admin");
        driver.findElement(By.id("password")).sendKeys("adminpass");
        driver.findElement(By.id("submit")).click();
        assertThat(driver.getCurrentUrl()).isEqualTo("https://localhost:"+this.port+"/index");
    }

    @Test
    public void getProfile() {
        driver.get("https://localhost:"+this.port+"/");
        driver.findElement(By.id("username")).sendKeys("admin");
        driver.findElement(By.id("password")).sendKeys("adminpass");
        driver.findElement(By.id("submit")).click();
        driver.findElement(By.id("profileHeader")).click();
        assertThat(driver.findElement(By.id("userName")).getText()).isEqualTo("admin");
    }

    @Test
    public void updateProfile() {
        driver.get("https://localhost:"+this.port+"/");
        driver.findElement(By.id("username")).sendKeys("admin");
        driver.findElement(By.id("password")).sendKeys("adminpass");
        driver.findElement(By.id("submit")).click();
        driver.findElement(By.id("profileHeader")).click();
        driver.findElement(By.id("updateProfile")).click();
        driver.findElement(By.id("username")).clear();
        driver.findElement(By.id("username")).sendKeys("admin2");
        driver.findElement(By.id("password")).sendKeys("admin");
        driver.findElement(By.id("confirmPassword")).sendKeys("adminpass");
        driver.findElement(By.id("submit")).click();
        assertThat(driver.findElement(By.id("userName")).getText()).isEqualTo("admin2");
    }
}
