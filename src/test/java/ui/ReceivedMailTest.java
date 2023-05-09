package ui;

import base.BaseTest;
import mail.MailDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pages.MainPage;
import pages.StartPage;
import utils.ReadProperties;

import static utils.Waiter.waitFor;

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
        mainPage.fillLetter(ReadProperties.getEmail(), subject, messageBody);
        waitFor(5000, "waiting for a message to be sent and received");
    }

    @Test
    public void checkReceivedMessageByPage() {
        messageData = mainPage.getDataForVerifyReceivedMessage();
        softAssertions.assertThat(messageData.getEmailFrom())
                .isEqualTo(email);
        softAssertions.assertThat(messageData.getEmailFrom())
                .isEqualTo(subject);
        softAssertions.assertThat(messageData.getEmailFrom())
                .isEqualTo(messageBody);
    }

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
