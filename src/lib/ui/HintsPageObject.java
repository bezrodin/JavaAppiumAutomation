package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;

public class HintsPageObject extends MainPageObject{
    private static final String
            CLOSE_BUTTON = "org.wikipedia:id/closeButton",
            CLOSE_WELCOME_ONBOARDING_BUTTON = "//android.view.View[@content-desc='Close']",
            CANCEL_BUTTON = "android:id/button2",
            HIDE_SYNC_READING_LISTS_BUTTON = "org.wikipedia:id/buttonView",
            GOT_IT_BUTTON = "//android.widget.Button[@text='Got it']",
            SAVE_ARTICLE_HINT_BUTTON = "org.wikipedia:id/create_button";

    public HintsPageObject(AppiumDriver driver){
        super(driver);
    }

    public void tapCloseWikipediaGamesOnboarding()
    {
        this.clickIfElementPresent(
                By.id(CLOSE_BUTTON),
                "Cannot find 'Close Wikipedia Games onboarding' button",
                15
        );
    }

    public void closeWelcomeOnboarding()
    {
            this.clickIfElementPresent(
                    By.xpath(CLOSE_WELCOME_ONBOARDING_BUTTON),
                    "Cannot find 'Close onboarding' button",
                    5
                    );
    }

    public void hideSyncReadingListsDialog(){
        this.clickIfElementPresent(
                By.id(CANCEL_BUTTON),
                "Cannot hide 'Sync reading lists' dialog",
                15
        );

    }

    public void hideSyncReadingListsButton(){
        this.clickIfElementPresent(
                By.id(HIDE_SYNC_READING_LISTS_BUTTON),
                "Cannot hide 'Sync reading lists' dialog",
                5
        );
    }

    public void tapGotItButton(){
        this.clickIfElementPresent(
                By.xpath(GOT_IT_BUTTON),
                "Cannot hide GOT IT hint",
                5
        );
    }

    public void tapMoveToReadingListHint(){
        this.clickIfElementPresent(
                By.id(SAVE_ARTICLE_HINT_BUTTON),
                "Cannot find Move to reading list snackbar",
                5
        );
    }


}
