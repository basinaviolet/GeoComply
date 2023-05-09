package base;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

abstract public class BasePage {
    protected static WebDriver driver;
    protected static WebDriverWait wait;
    protected static final Logger logger = (Logger) LogManager.getLogger(BasePage.class.getName());

    public BasePage(){
        PageFactory.initElements(driver, this);
        logger.info("Create driver session");
    }

    public static void setDriver(WebDriver webDriver, WebDriverWait webDriverWait){
        driver = webDriver;
        wait = webDriverWait;
    }

    public void clickForElementWithWaiter(WebElement element) {
        logger.debug("Waiting for an element {} to be clicked", element);
        wait.until(ExpectedConditions.refreshed(ExpectedConditions.elementToBeClickable(element)));
        element.click();
    }

    public boolean isElementDisplayedWithWaiter(WebElement element) {
        logger.debug("Waiting for an element {} to be visible", element);
        wait.until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOf(element)));
        return element.isDisplayed();
    }

    public boolean isTitleTextContainsWithWaiter(String titleText) {
        logger.debug("Waiting for title text");
        return wait.until(ExpectedConditions.refreshed(ExpectedConditions.titleContains(titleText)));
    }

    public String getCurrentUrlWithoutWaiter() {
        logger.debug("Waiting for current url");
        return driver.getCurrentUrl();
    }

    public String getTextWithWaiter(WebElement element) {
        logger.debug("Waiting for an element {} to be visible", element);
        wait.until(ExpectedConditions.visibilityOf(element));
        logger.info("Get text attribute for an element {}", element);
        return element.getText();
    }

    public String getValueOfAttributeWithoutWaiter(WebElement element, String attribute) {
        logger.debug("Get value of attribute for an element {}", element);
        return element.getAttribute(attribute);
    }

    public void sendKeysTextWithWaiter(WebElement element, String text) {
        logger.debug("Waiting for an element {} to be visible", element);
        wait.until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOf(element)));
        element.sendKeys(text);
        logger.info("Send text {} for element {}", text, element);
    }

    public void sendKeysAndClickEnter(WebElement element, String text) {
        logger.debug("Waiting for an element {} to be visible", element);
        wait.until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOf(element)));
        element.sendKeys(text, Keys.ENTER);
        logger.info("Send text {} for element {} and click enter", text, element);
    }
}
