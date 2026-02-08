package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;

public class NavigationUI extends MainPageObject{
    private static final String
            BACK_BUTTON = "//*[@content-desc='Navigate up']",
            NAV_TAB_READING_LISTS= "org.wikipedia:id/nav_tab_reading_lists";
    public NavigationUI(AppiumDriver driver){
        super(driver);
    }

    public void tapBackButton()
    {
        this.waitForElementAndClick(
                By.xpath(BACK_BUTTON),
                "Unable return to find 'Back' button",
                5
        );
    }

    public void tapNavTabReadingLists(){
        this.waitForElementAndClick(
                By.id(NAV_TAB_READING_LISTS),
                "Unable to find NAV TAB READING LISTS",
                5
        );
    }

    public void tapBackButtonOnDevice(){
        this.waitForElementAndClick(
                By.id(NAV_TAB_READING_LISTS),
                "Unable to find NAV TAB READING LISTS",
                5
        );
    }

}
