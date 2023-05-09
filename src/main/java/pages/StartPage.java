package pages;

import base.BasePage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import utils.ReadProperties;

public class StartPage extends BasePage {

    private static final String email = ReadProperties.getEmail();
    private final String password = ReadProperties.getPassword();
    protected static final Logger logger = (Logger) LogManager.getLogger(StartPage.class);

    @FindBy(xpath = "//input[@type='email']")
    private WebElement emailInputField;

    @FindBy(xpath = "//input[@type='password']")
    private WebElement passwordInputField;

    @FindBy(id = "identifierNext")
    private WebElement emailNextButton;

    @FindBy(id = "passwordNext")
    private WebElement passwordNextButton;

    @FindBy(xpath = "//span[@aria-label]")
    private WebElement languageSetting;

    @FindBy(xpath = "//div[@role='combobox']")
    private WebElement settingsBox;

    @FindBy(xpath = "//li[@data-value='en-US']")
    private WebElement englishLanguage;

    public StartPage(){
        driver.get(ReadProperties.getUrl());
    }

    public boolean isEmailFieldDisplayed(){
        return isElementDisplayedWithWaiter(emailInputField);
    }

    public boolean isEmailNextBtnDisplayed(){
        return isElementDisplayedWithWaiter(emailNextButton);
    }

    public boolean isPasswordFieldDisplayed(){
        return isElementDisplayedWithWaiter(passwordInputField);
    }

    public boolean isPasswordNextBtnDisplayed(){
        return isElementDisplayedWithWaiter(passwordNextButton);
    }

    public String getLanguageSettings(){
        return getTextWithWaiter(languageSetting);
    }

    public void checkEnglish(){
        if (!getTextWithWaiter(languageSetting).equals("English (United States)")){
            clickForElementWithWaiter(settingsBox);
            try {
                clickForElementWithWaiter(englishLanguage);
                logger.info("switch to English language");
            } catch (Exception e) {
                logger.error("error during execution: found language");
            }
        }
    }

    public void fillEmail() {
        sendKeysTextWithWaiter(emailInputField, email);
        clickForElementWithWaiter(emailNextButton);
    }

    public MainPage fillPassword() {
        sendKeysTextWithWaiter(passwordInputField, password);
        clickForElementWithWaiter(passwordNextButton);
        return new MainPage();
    }
}
