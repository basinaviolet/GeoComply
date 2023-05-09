package ui;

import base.BaseTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pages.MainPage;
import pages.StartPage;
import utils.ReadProperties;

public class SendMailTest extends BaseTest {
    protected MainPage mainPage;
    String subject = mailUtils.getMessageData("subject");
    String messageBody = mailUtils.getMessageData("messageBody");
    String email = ReadProperties.getEmail();

    @BeforeEach
    public void moveToMainPage() {
        StartPage page = new StartPage();
        page.checkEnglish();
        page.fillEmail();
        mainPage = page.fillPassword();
    }

    @Test
    public void createNewMessageWithValidData() {
        softAssertions.assertThat(mainPage.isComposeButtonDisplayed())
                .withFailMessage("The compose new letter button on the main page is not displayed")
                .isTrue();
        mainPage.clickComposeNewMessageBtn();

        softAssertions.assertThat(mainPage.isRecipientFieldDisplayed())
                .withFailMessage("The recipient field on the main page is not displayed")
                .isTrue();
        softAssertions.assertThat(mainPage.getRecipientFieldText())
                .isEqualTo("To");

        softAssertions.assertThat(mainPage.isSubjectFieldDisplayed())
                .withFailMessage("The subject field on the main page is not displayed")
                .isTrue();
        softAssertions.assertThat(mainPage.getSubjectFieldText())
                .isEqualTo("Subject");
        softAssertions.assertThat(mainPage.isMessageBodyFieldDisplayed())
                .withFailMessage("The field for message entering on the main page is not displayed")
                .isTrue();

        softAssertions.assertThat(mainPage.isSendButtonDisplayed())
                .withFailMessage("The send message button on the main page is not displayed")
                .isTrue();
        softAssertions.assertThat(mainPage.getSendButtonText())
                .isEqualTo("Send");

        mainPage.fillLetter(ReadProperties.getEmail(), subject, messageBody);
    }
}
