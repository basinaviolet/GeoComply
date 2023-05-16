package base;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.virtualauthenticator.Credential;
import org.openqa.selenium.virtualauthenticator.HasVirtualAuthenticator;
import org.openqa.selenium.virtualauthenticator.VirtualAuthenticator;
import org.openqa.selenium.virtualauthenticator.VirtualAuthenticatorOptions;
import utils.ReadProperties;

import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

abstract public class BasePage {
    protected static WebDriver driver;
    protected static WebDriverWait wait;
    protected static final Logger logger = (Logger) LogManager.getLogger(BasePage.class.getName());

    private final static String base64EncodedKey = ReadProperties.getBase64EncodedKey();
    private final static String base64EncodedID = ReadProperties.getBase64EncodedID();
    private final static String base64UserHandle = ReadProperties.getBase64UserHandle();
    private final static String rpID = ReadProperties.getRpId();

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

    protected void setupVirtualAuthenticator() {
        logger.info("Preparing virtual authenticator. Using passkeys authenticator");
        PKCS8EncodedKeySpec ec256PrivateKey =
                new PKCS8EncodedKeySpec(Base64.getMimeDecoder().decode(base64EncodedKey));
        byte[] credentialId = Base64.getMimeDecoder().decode(base64EncodedID);
        byte[] userHandle = Base64.getMimeDecoder().decode(base64UserHandle);

        // get the session id
        VirtualAuthenticatorOptions options = new VirtualAuthenticatorOptions()
                .setProtocol(VirtualAuthenticatorOptions.Protocol.CTAP2)
                .setTransport(VirtualAuthenticatorOptions.Transport.USB)
                .setHasResidentKey(true)
                .setHasUserVerification(true)
                .setIsUserVerified(true);

        VirtualAuthenticator authenticator = ((HasVirtualAuthenticator) driver).addVirtualAuthenticator(options);

        Credential residentCredential = Credential.createResidentCredential(
                credentialId, rpID, ec256PrivateKey, userHandle, 1);

        authenticator.addCredential(residentCredential);
        logger.info("Ending virtual authenticator.");
    }
}
