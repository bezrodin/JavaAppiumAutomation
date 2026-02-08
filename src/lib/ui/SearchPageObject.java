package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;


public class SearchPageObject extends MainPageObject{

    private static final String
            SEARCH_INIT_ELEMENT = "//*[contains(@text,'Search Wikipedia')]",
            SEARCH_INPUT = "org.wikipedia:id/search_src_text",
            SEARCH_BACK_BUTTON = "//*[@content-desc='Navigate up']",
            SEARCH_RESULT_BY_SUBSTRING_TPL = "//*[@resource-id='org.wikipedia:id/page_list_item_description' and @text='{SUBSTRING}']",
            SNACK_BAR_ELEMENT = "//*[@resource-id='org.wikipedia:id/snackbar_text' and @text='You can access your Year in Review later in the More menu.']";


    public SearchPageObject(AppiumDriver driver){
        super(driver);
    }

    /* TEMPLATES METHODS */
    public static String getResultSearchElement(String substring){
        return SEARCH_RESULT_BY_SUBSTRING_TPL.replace("{SUBSTRING}", substring);
    }
    /* TEMPLATES METHODS */

    public void initSearchInput(){
        this.waitForElementPresent(
                By.xpath(SEARCH_INIT_ELEMENT),
                "Cannot find search input after clicking search init element");
        this.waitForElementAndClick(
                By.xpath(SEARCH_INIT_ELEMENT),
                "Cannot find and click search init element",
                5);
    }

    public void typeSearchLine(String search_line){
        this.waitForElementAndSendKeys(
                By.id(SEARCH_INPUT),
                search_line,
                "Cannot find and type into search input",
                5);
    }

    public void waitForSearchResult(String substring){
        String search_result_xpath = getResultSearchElement(substring);
        this.waitForElementPresent(
                By.xpath(search_result_xpath),
                "Cannot find search result with substring " + substring,
                5);
    }

    public void waitForBackButtonToAppear(){
        this.waitForElementPresent(
                By.xpath(SEARCH_BACK_BUTTON),
                "Cannot find BACK button",
                5);
    }

    public void waitForBackButtonToDisappear(){
        this.waitForElementNotPresent(
                By.xpath(SEARCH_BACK_BUTTON),
                "BACK button is still present",
                5);
    }

    public void clickBackButton(){
        this.waitForElementAndClick(
                By.xpath(SEARCH_BACK_BUTTON),
                "Cannot click BACK button",
                5
        );
    }

    public void clearSearchField(){
        this.waitForElementAndClear(
                By.id(SEARCH_INPUT),
                "Cannot clear SEARCH field",
                5
        );
    }

    public void clickByArticleWithSubstring(String substring){
        String search_result_xpath = getResultSearchElement(substring);
        this.waitForElementAndClick(
                By.xpath(search_result_xpath),
                "Cannot find and click search result with substring " + substring,
                10);
    }

    public void waitForSnackBarToDisappear(){
        this.waitForElementNotPresent(
                By.xpath(SNACK_BAR_ELEMENT),
                "Snack Bar is still present",
                10);
    }
}
