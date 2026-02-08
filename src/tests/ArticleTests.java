package tests;

import lib.CoreTestCase;
import lib.ui.ArticlePageObject;
import lib.ui.HintsPageObject;
import lib.ui.SearchPageObject;
import org.junit.Test;
import org.openqa.selenium.By;

public class ArticleTests extends CoreTestCase {
    @Test
    public void testCompareArticleTitle() {
        SearchPageObject SearchPageObject = new SearchPageObject(driver);
        HintsPageObject HintsPageObject = new HintsPageObject(driver);
        SearchPageObject.initSearchInput();
        HintsPageObject.closeWelcomeOnboarding();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.clickByArticleWithSubstring("Object-oriented programming language");

        HintsPageObject.tapCloseWikipediaGamesOnboarding();
        ArticlePageObject ArticlePageObject = new ArticlePageObject(driver);
        ArticlePageObject.waitForContentsAndClick();
        String article_title = ArticlePageObject.getArticleTitle();

        assertEquals("We see unexpected title!",
                "Java (programming language)",
                article_title);

    }

    @Test
    public void testSwipeArticle() {
        SearchPageObject SearchPageObject = new SearchPageObject(driver);
        HintsPageObject HintsPageObject = new HintsPageObject(driver);
        SearchPageObject.initSearchInput();
        HintsPageObject.closeWelcomeOnboarding();
        SearchPageObject.typeSearchLine("Appium");
        SearchPageObject.clickByArticleWithSubstring("Automation for Apps");

        HintsPageObject.tapCloseWikipediaGamesOnboarding();
        ArticlePageObject ArticlePageObject = new ArticlePageObject(driver);
        ArticlePageObject.waitForContentsButton();
        ArticlePageObject.swipeToFooter();
    }
}
