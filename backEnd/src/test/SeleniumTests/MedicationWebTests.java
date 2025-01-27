package SeleniumTests;

import EvaRuiz.HealthCarer.HealthCarerApplication;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.chromium.ChromiumDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.time.Duration;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes= HealthCarerApplication.class)
public class MedicationWebTests {

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
        driver.findElement(By.id("medications")).click();
    }
    @AfterEach
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void getMedications() {
        
        assertThat(driver.getCurrentUrl()).isEqualTo("https://localhost:"+this.port+"/medications/");
    }

    @Test
    public void createMedication() {
        
        driver.findElement(By.id("createMedication")).click();
        driver.findElement(By.id("name")).sendKeys("TestMedication");
        driver.findElement(By.id("stock")).sendKeys("10");
        driver.findElement(By.id("instructions")).sendKeys("TestInstructions");
        driver.findElement(By.id("dose")).sendKeys("5");
        driver.findElement(By.id("boxImage")).sendKeys("C:\\Users\\alcor\\Desktop\\2024-HealthCarer\\backEnd\\src\\main\\resources\\static\\images\\no_image.png");
        driver.findElement(By.id("submit")).click();
        assertThat(driver.findElement(By.id("medicationName")).getText()).isEqualTo("TestMedication");
    }

    @Test
    public void updateMedication() {
        driver.findElement(By.id("medicationName")).click();
        driver.findElement(By.id("updateMedication")).click();
        driver.findElement(By.id("name")).clear();
        driver.findElement(By.id("name")).sendKeys("UpdatedMedication");
        driver.findElement(By.id("boxImage")).sendKeys("C:\\Users\\alcor\\Desktop\\2024-HealthCarer\\backEnd\\src\\main\\resources\\static\\images\\healthcarer.png");
        driver.findElement(By.id("submit")).click();
        assertThat(driver.findElement(By.id("medicationName")).getText()).isEqualTo("UpdatedMedication");
    }

}
