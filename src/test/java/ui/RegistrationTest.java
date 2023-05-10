package ui;

import base.BaseTest;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.MainPage;
import pages.StartPage;
import utils.ReadProperties;

@Epic("Gmail mailbox")
@Feature("Registration mail")
public class RegistrationTest extends BaseTest {

    @Severity(SeverityLevel.BLOCKER)
    @Description("Checking registration flow")
    @DisplayName("Registration flow with valid data")
    @Test
    public void moveToEmailPageTest() {
        StartPage page = new StartPage();
        softAssertions.assertThat(page.isEmailFieldDisplayed())
                .withFailMessage("The email field is not displayed")
                .isTrue();
        softAssertions.assertThat(page.isEmailNextBtnDisplayed())
                .withFailMessage("The continue button on the entering email page is not displayed")
                .isTrue();
        page.checkEnglish();
        softAssertions.assertThat(page.getLanguageSettings())
                .withFailMessage("The language is not english")
                .isEqualTo("English (United States)");
        page.fillEmail();
        softAssertions.assertThat(page.isPasswordFieldDisplayed())
                .withFailMessage("The email field is not displayed")
                .isTrue();
        softAssertions.assertThat(page.isPasswordNextBtnDisplayed())
                .withFailMessage("The continue button on the entering email page is not displayed")
                .isTrue();
        MainPage mainPage = page.fillPassword();
        softAssertions.assertThat(mainPage.isTitleTextContains(ReadProperties.getEmail().toLowerCase()))
                .withFailMessage("The page title does not contain the user`s e-mail")
                .isTrue();
        softAssertions.assertThat(mainPage.getTitlePageText(ReadProperties.getEmail().toLowerCase()))
                .isEqualTo(ReadProperties.getUrl());
    }
}
