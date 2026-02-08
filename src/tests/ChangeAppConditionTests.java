package tests;

import lib.CoreTestCase;
import lib.ui.ArticlePageObject;
import lib.ui.HintsPageObject;
import lib.ui.SearchPageObject;
import org.junit.Test;

public class ChangeAppConditionTests extends CoreTestCase {
    @Test
    public void testChangeScreenOrientationOnSearchResults(){
        SearchPageObject SearchPageObject = new SearchPageObject(driver);
        HintsPageObject HintPageObject = new HintsPageObject(driver);
        ArticlePageObject ArticlePageObject = new ArticlePageObject(driver);

        SearchPageObject.initSearchInput();
        HintPageObject.closeWelcomeOnboarding();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.clickByArticleWithSubstring("Object-oriented programming language");
        HintPageObject.tapCloseWikipediaGamesOnboarding();

        String title_before_rotation = ArticlePageObject.getArticleDescription();
//        System.out.println("Title Before Rotation is: " + title_before_rotation);
        this.rotateScreenLandscape();
        String title_after_rotation = ArticlePageObject.getArticleDescription();
//        System.out.println("Title After Rotation is: " + title_after_rotation);

        assertEquals(
                "Article title have been changed after screen rotation",
                title_before_rotation,
                title_after_rotation
        );

        this.rotateScreenPortrait();

        String title_after_second_rotation = ArticlePageObject.getArticleDescription();

        assertEquals(
                "Article title have been changed after screen rotation",
                title_before_rotation,
                title_after_second_rotation
        );
    }

    @Test
    public void testCheckSearchArticleInBackground(){
        SearchPageObject SearchPageObject = new SearchPageObject(driver);
        HintsPageObject HintPageObject = new HintsPageObject(driver);

        SearchPageObject.initSearchInput();
        HintPageObject.closeWelcomeOnboarding();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.waitForSearchResult("Object-oriented programming language");
        this.backgroundApp(3);
        SearchPageObject.waitForSearchResult("Object-oriented programming language");
    }
}
