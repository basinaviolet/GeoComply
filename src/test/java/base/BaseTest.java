package base;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.MailUtils;

import java.time.Duration;

abstract public class BaseTest {

    protected WebDriver driver;
    protected WebDriverWait wait;

    public SoftAssertions softAssertions = new SoftAssertions();
    protected MailUtils mailUtils = new MailUtils();
    protected static final Logger logger = (Logger) LogManager.getLogger(BaseTest.class);

    @BeforeEach
    public void previous() {
        logger.debug("Setting up the driver");
        setUp();
    }

    @AfterEach
    public void ending() {
        logger.debug("Checking all assertions");
        softAssertions.assertAll();
        logger.debug("Closing the page and the driver");
        complete();
    }

    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        BasePage.setDriver(driver, wait);
    }

    public void complete() {
        driver.close();
        driver.quit();
    }
}
