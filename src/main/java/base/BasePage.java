package base;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.Command;
import org.openqa.selenium.remote.CommandInfo;
import org.openqa.selenium.remote.HttpCommandExecutor;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.SessionId;
import org.openqa.selenium.remote.http.HttpMethod;
import org.openqa.selenium.remote.service.DriverCommandExecutor;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

abstract public class BasePage {
    protected static WebDriver driver;
    protected static WebDriverWait wait;
    protected static final Logger logger = (Logger) LogManager.getLogger(BasePage.class.getName());

    public BasePage() {
        PageFactory.initElements(driver, this);
        logger.info("Create driver session");
    }

    public static void setDriver(WebDriver webDriver, WebDriverWait webDriverWait) {
        driver = webDriver;
        wait = webDriverWait;
    }

    public void clickForElementWithWaiter(WebElement element) {
        logger.debug("Waiting for an element {} to be clicked", element);
        wait.until(ExpectedConditions.refreshed(ExpectedConditions.elementToBeClickable(element)));
        logger.info("Clicking the {} button", element);
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

    public String getCurrentUrlWithWaiter(String titleText) {
        if (isTitleTextContainsWithWaiter(titleText)) {
            logger.debug("Waiting for current url");
        } else logger.debug("This is the wrong url");
        logger.info("Getting current url {}", driver.getCurrentUrl());
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

    protected void setupVirtualAuthenticator(){
        try {
            logger.debug("Getting passkeys authenticator");
            // get the session id
            RemoteWebDriver remoteWebDriver = (RemoteWebDriver) driver;
            SessionId sessionId = remoteWebDriver.getSessionId();

            DriverCommandExecutor commandExecutor = (DriverCommandExecutor) remoteWebDriver.getCommandExecutor();
            CommandInfo commandInfo = new CommandInfo("/session/:sessionId/webauthn/authenticator", HttpMethod.POST);

            // using reflection to access protected method
            Method defineCommand = HttpCommandExecutor.class.getDeclaredMethod("defineCommand", String.class, CommandInfo.class);
            defineCommand.setAccessible(true);
            defineCommand.invoke(commandExecutor, "AddVirtualAuthenticator", commandInfo);

            // executing new 'add virtual authenticator' command
            Map<String, String> params = Map.of("protocol", "ctap2", "transport", "internal");
            Command addVirtualAuthCommand = new Command(sessionId, "AddVirtualAuthenticator", params);
            commandExecutor.execute(addVirtualAuthCommand);
        }catch (IOException | InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
            logger.error("Passkeys authenticator were not received");
            throw new RuntimeException(e);
        }
    }
}
