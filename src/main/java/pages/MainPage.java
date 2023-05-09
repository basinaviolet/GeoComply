package pages;

import base.BasePage;
import mail.MailDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MainPage extends BasePage {
    protected static final Logger logger = (Logger) LogManager.getLogger(MainPage.class);
    MailDTO messageData;

    @FindBy(xpath = "//div[@role='button' and text()=\"Compose\"]")
    private WebElement createNewLetter;

    @FindBy(xpath = "//div[text()=\"Recipients\"]")
    private WebElement recipientField;
    @FindBy(xpath = "//div[@name=\"to\"]//input")
    private WebElement recipientTOField;
    @FindBy(xpath = "//span[contains(@aria-label,\"Select contacts\")]")
    private WebElement recipientToFieldText;

    @FindBy(xpath = "//input[@name=\"subjectbox\"]")
    private WebElement subjectField;
    String subjectTextAttribute = "placeholder"; //text

    @FindBy(xpath = "//div[@aria-label='Message Body']")
    private WebElement messageBodyField;

    @FindBy(xpath = "//div[@role='button' and contains(@data-tooltip,\"Send\")]")
    private WebElement sendButton;

    //----box of received emails-----
    @FindBy(xpath = "//div[@aria-label='Primary']")
    private WebElement primaryBox;
    String selectedAttribute = "aria-label"; //true

    @FindBy(xpath = "//div[@class='UI']//tbody//tr[1]//span[@email]")
    private WebElement checkEmail;

    @FindBy(xpath = "//div[@class='UI']//tbody//tr[1]/td[@role='gridcell']/div//span")
    private List<WebElement> lastReceivedMailDataList;


    public MainPage() {
        super();
    }

    public boolean isComposeButtonDisplayed() {
        return isElementDisplayedWithWaiter(createNewLetter);
    }

    public boolean isRecipientFieldDisplayed() {
        return isElementDisplayedWithWaiter(recipientTOField);
    }

    public String getRecipientFieldText() {
        return getTextWithWaiter(recipientToFieldText);
    }

    public boolean isSubjectFieldDisplayed() {
        return isElementDisplayedWithWaiter(subjectField);
    }

    public String getSubjectFieldText() {
        return getValueOfAttributeWithoutWaiter(subjectField, subjectTextAttribute);
    }

    public boolean isMessageBodyFieldDisplayed() {
        return isElementDisplayedWithWaiter(messageBodyField);
    }

    public boolean isSendButtonDisplayed() {
        return isElementDisplayedWithWaiter(sendButton);
    }

    public String getSendButtonText() {
        return getTextWithWaiter(sendButton);
    }

    public String getTitlePageText() {
        return driver.getTitle();
    }
    public String getTitlePageText1() {
        return getCurrentUrlWithoutWaiter();
    }

    public boolean isTitleTextContains(String title) {
        return isTitleTextContainsWithWaiter(title);
    }

    public void setTextToField(WebElement element, String text) {
        sendKeysAndClickEnter(element, text);
    }

    public void clickComposeNewMessageBtn() {
        clickForElementWithWaiter(createNewLetter);
    }

    public void fillLetter(String recipient, String subject, String body) {
        setTextToField(recipientTOField, recipient);
        setTextToField(subjectField, subject);
        setTextToField(messageBodyField, body);
        clickForElementWithWaiter(sendButton);
    }

    public void goToPrimaryBox() {
        isElementDisplayedWithWaiter(primaryBox);
        if (!getValueOfAttributeWithoutWaiter(primaryBox, selectedAttribute).equals("true")) {
            clickForElementWithWaiter(primaryBox);
        }
    }

    public MailDTO getDataForVerifyReceivedMessage() {
        goToPrimaryBox();
        if (isElementDisplayedWithWaiter(checkEmail)) {
            messageData.setEmailFrom(checkEmail.getAttribute("email"));
            messageData.setSender(checkEmail.getAttribute("name"));
        }
        getMessageData();
        return messageData;
    }

    public void getMessageData() {
        Set<String> messageDataSet = new HashSet<>();
        if (lastReceivedMailDataList.size() > 0) {
            for (WebElement element : lastReceivedMailDataList) {
                if (!element.getText().equals("")) messageDataSet.add(element.getText());
            }
            fillMailContainer(messageDataSet);
        } else logger.error("Elements {} of mail were mot found", lastReceivedMailDataList);
    }

    public void fillMailContainer(Set<String> data){
        if (!data.isEmpty()) {
            Object[] bodyElements = data.toArray();
            messageData.setSubject(String.valueOf(bodyElements[1]));
            messageData.setBody(String.valueOf(bodyElements[3])
                    .replace(String.valueOf(bodyElements[0]), "")
                    .replace(String.valueOf(bodyElements[1]), ""));
        } else logger.error("Elements of mail were mot found");
    }
}
