package ui;

import base.BaseTest;
import org.junit.jupiter.api.Test;
import pages.MainPage;
import pages.StartPage;
import utils.ReadProperties;

public class StartPageTest extends BaseTest {

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
        softAssertions.assertThat(mainPage.getTitlePageText())
                .isEqualTo("Gmail");
        softAssertions.assertThat(mainPage.getTitlePageText1())
                .isEqualTo(ReadProperties.getUrl());
        softAssertions.assertThat(mainPage.isTitleTextContains("geocomplytestvb@gmail.com"))
                .withFailMessage("The page title does not contain the user`s e-mail")
                .isTrue();
    }
}
