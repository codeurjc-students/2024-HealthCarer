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
public class TreatmentWebTests {

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
        driver.findElement(By.id("treatments")).click();
    }
    @AfterEach
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void getTreatments() {
        assertThat(driver.getCurrentUrl()).isEqualTo("https://localhost:"+this.port+"/treatments/");
        assertThat(driver.findElement(By.id("treatmentsList")).isDisplayed());

    }


    @Test
    public void createTreatment() {
        driver.findElement(By.id("createTreatment")).click();
        driver.findElement(By.id("name")).sendKeys("TestTreatment");
        driver.findElement(By.id("startDate")).sendKeys("30-01-2024");
        driver.findElement(By.id("endDate")).sendKeys("03-10-2025");
        driver.findElement(By.id("startTime")).sendKeys("10:00");
        driver.findElement(By.id("dispensingFrequency")).sendKeys("5");
        driver.findElement(By.id("medications")).findElements(By.id("medication")).getFirst().click();
        driver.findElement(By.id("submit")).click();
        assertThat(driver.findElement(By.id("treatmentName")).getText().contains("TestTreatment"));
    }

    @Test
    public void updateTreatment() {
        driver.findElement(By.id("treatmentName")).click();
        driver.findElement(By.id("updateTreatment")).click();
        driver.findElement(By.id("name")).clear();
        driver.findElement(By.id("name")).sendKeys("TestTreatment2");
        driver.findElement(By.id("endDate")).clear();
        driver.findElement(By.id("endDate")).sendKeys("03-10-2028");
        driver.findElement(By.id("startDate")).sendKeys("03-10-2025");
        driver.findElement(By.id("submit")).click();
        assertThat(driver.findElement(By.id("treatmentName")).getText().contains("TestTreatment2"));
    }

    @Test
    public void deleteTreatment() {
        driver.findElement(By.id("treatmentName")).click();
        driver.findElement(By.id("deleteTreatment")).click();
        assertThat(driver.findElement(By.id("treatmentsList")).getText()).doesNotContain("Tratamiento1");
    }
}
