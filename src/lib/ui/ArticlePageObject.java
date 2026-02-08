package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class ArticlePageObject extends MainPageObject{
    private static final String
    CONTENTS_BUTTON = "org.wikipedia:id/page_contents",
    CONTENTS_ARTICLE_TITLE = "org.wikipedia:id/page_toc_item_text",
    FOOTER_ELEMENT =  "//*[@resource-id='pcs-footer-container-legal']",
    SAVE_BUTTON = "org.wikipedia:id/page_save",
    SNACK_BAR_ACTION = "org.wikipedia:id/snackbar_action",
    NAME_OF_LIST_FIELD = "org.wikipedia:id/text_input",
    NAME_OF_LIST_DESCRIPTION = "org.wikipedia:id/secondary_text_input",
    OK_BUTTON = "android:id/button1"
    ;


    public ArticlePageObject(AppiumDriver driver){
        super(driver);
    }

    public WebElement waitForContentsButton(){
        return this.waitForElementPresent(
                By.id(CONTENTS_BUTTON),
                "Cannot find 'Contents' button",
                15);
    }

    public WebElement waitForContentsAndClick(){
        return this.waitForElementAndClick(
                By.id(CONTENTS_BUTTON),
                "Cannot find 'Contents' button",
                15);
    }

    public WebElement waitForSaveButtonAndClick(){
        return this.waitForElementAndClick(
                By.id(SAVE_BUTTON),
                "Cannot find 'Save page' button",
                15);
    }

    public WebElement waitForSnackBarActionAndClick(){
        return this.waitForElementAndClick(
                By.id(SNACK_BAR_ACTION),
                "Cannot find 'Save page' button",
                15);
    }

    public WebElement waitForTitleElement(){
        return this.waitForElementPresent(
                By.id(CONTENTS_ARTICLE_TITLE),
                "Cannot find Article Title",
                15);
    }

    public String getArticleTitle(){
        WebElement title_element = waitForTitleElement();
        System.out.println(title_element.getAttribute("text"));
        return title_element.getAttribute("text");
    }

    public void swipeToFooter(){
        this.swipeUpToFindElement(
                By.xpath(FOOTER_ELEMENT),
                "Cannot find the end of the article",
                20
        );
    }

   public void addArticleToMyList(String name_of_folder, String name_of_description){
       this.waitForElementAndSendKeys(
               By.id(NAME_OF_LIST_FIELD),
               name_of_folder,
               "Cannot find 'Name of this list' field",
               5);

       this.waitForElementAndSendKeys(
               By.id(NAME_OF_LIST_DESCRIPTION),
               name_of_description,
               "Cannot find 'Name of description' field",
               5);


       this.waitForElementAndClick(
               By.id(OK_BUTTON),
               "Cannot tap OK button",
               5
       );

   }

}
