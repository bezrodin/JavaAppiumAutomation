package tests;

import lib.CoreTestCase;
import lib.ui.*;
import org.junit.Test;

public class MyListsTests extends CoreTestCase {
    @Test
    public void testSaveFirstArticleToMyList()
    {
        SearchPageObject SearchPageObject = new SearchPageObject(driver);
        ArticlePageObject ArticlePageObject = new ArticlePageObject(driver);
        HintsPageObject HintsPageObject = new HintsPageObject(driver);
        NavigationUI NavigationUI = new NavigationUI(driver);
        MyListsPageObject MyListsPageObject  = new MyListsPageObject(driver);

        SearchPageObject.initSearchInput();
        HintsPageObject.closeWelcomeOnboarding();
        SearchPageObject.waitForSnackBarToDisappear();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.clickByArticleWithSubstring("Object-oriented programming language");
        HintsPageObject.tapCloseWikipediaGamesOnboarding();
        ArticlePageObject.waitForContentsAndClick();
        String article_title = ArticlePageObject.getArticleTitle();
        NavigationUI.tapBackButton();
        ArticlePageObject.waitForSaveButtonAndClick();
        ArticlePageObject.waitForSnackBarActionAndClick();
        String name_of_folder = "Favorite";

        ArticlePageObject.addArticleToMyList(
                name_of_folder,
                "Read Later"
        );

        NavigationUI.tapBackButton();
        NavigationUI.tapBackButton();
        HintsPageObject.hideSyncReadingListsDialog();
        NavigationUI.tapNavTabReadingLists();
        MyListsPageObject.openFolderByName(name_of_folder);
        HintsPageObject.hideSyncReadingListsButton();
        HintsPageObject.tapGotItButton();
        MyListsPageObject.swipeByArticleToDelete(article_title);
    }
}
