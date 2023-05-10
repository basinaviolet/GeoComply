package ui;

import base.BaseTest;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import mail.MailDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.MainPage;
import pages.StartPage;
import utils.ReadProperties;

@Epic("Gmail mailbox")
@Feature("Received mail")
public class ReceivedMailTest extends BaseTest {
    protected MainPage mainPage;
    String subject = mailUtils.getMessageData("subject");
    String messageBody = mailUtils.getMessageData("messageBody");
    String email = ReadProperties.getEmail();
    MailDTO messageData;

    @BeforeEach
    public void moveToMainPageAndSendEmail() {
        StartPage page = new StartPage();
        page.checkEnglish();
        page.fillEmail();
        mainPage = page.fillPassword();
        mainPage.clickComposeNewMessageBtn();
        mainPage.fillAndSendLetter(ReadProperties.getEmail(), subject, messageBody);
    }

    @Severity(SeverityLevel.CRITICAL)
    @Description("Checking the data of the received message. By Web")
    @DisplayName("Received message data matches the actual data")
    @Test
    public void checkReceivedMessageByPage() {
        messageData = mainPage.getDataForVerifyReceivedMessage();
        softAssertions.assertThat(messageData.getEmailFrom())
                .isEqualTo(email.toLowerCase());
        softAssertions.assertThat(messageData.getSender())
                .isEqualTo("me");
        softAssertions.assertThat(messageData.getSubject())
                .isEqualTo(subject);
        softAssertions.assertThat(messageData.getBody())
                .isEqualTo(messageBody);
    }

    @Disabled("Needs Gmail 2-step verification")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Checking the data of the received message. By JavaMail")
    @DisplayName("Received message data matches the actual data")
    @Test
    public void checkReceivedMessageByJavaMail() {
        messageData = mailUtils.getMessageFromEmail();
        softAssertions.assertThat(messageData.getEmailFrom())
                .contains(email.toLowerCase());
        softAssertions.assertThat(messageData.getSubject())
                .isEqualTo(subject);
        softAssertions.assertThat(messageData.getBody())
                .contains(messageBody);
    }
}
