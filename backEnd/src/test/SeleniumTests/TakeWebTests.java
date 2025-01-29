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
public class TakeWebTests {

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
        driver.get("https://localhost:"+this.port+"/");
        driver.findElement(By.id("username")).sendKeys("admin");
        driver.findElement(By.id("password")).sendKeys("adminpass");
        driver.findElement(By.id("submit")).click();
        driver.findElement(By.id("takes")).click();
    }
    @AfterEach
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void getTakes() {
        assertThat(driver.getCurrentUrl()).isEqualTo("https://localhost:"+this.port+"/takes/");
        assertThat(driver.findElement(By.id("takesList")).getText()).contains("2021-01-01 00:00:00.0");
    }

    @Test
    public void createTake() {
        driver.findElement(By.id("createTake")).click();
        driver.findElement(By.id("date")).sendKeys("30-01-2024");
        driver.findElement(By.id("medications")).findElements(By.id("medication")).getFirst().click();
        driver.findElement(By.id("submit")).click();
        assertThat(driver.findElement(By.id("takeDetails")).getText()).contains("2024-01-30");
    }

    @Test
    public void updateTake() {
        driver.findElement(By.id("takeDate")).click();
        driver.findElement(By.id("updateTake")).click();
        driver.findElement(By.id("date")).sendKeys("29-01-2024");
        driver.findElement(By.id("submit")).click();
        assertThat(driver.findElement(By.id("takeDetails")).getText()).contains("2024-01-29");
    }

    @Test
    public void deleteTake() {
        driver.findElement(By.id("takeDate")).click();
        driver.findElement(By.id("deleteTake")).click();
        assertThat(driver.findElement(By.id("takesList")).getText()).doesNotContain("2024-01-30");
    }
}
