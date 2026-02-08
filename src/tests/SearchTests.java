package tests;

import lib.CoreTestCase;
import lib.ui.HintsPageObject;
import lib.ui.SearchPageObject;
import org.junit.Test;
import org.openqa.selenium.By;

public class SearchTests extends CoreTestCase {
    @Test
    public void testSearch() {
        SearchPageObject SearchPageObject = new SearchPageObject(driver);
        HintsPageObject HintPageObject = new HintsPageObject(driver);
        SearchPageObject.initSearchInput();
        HintPageObject.closeWelcomeOnboarding();


        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.waitForSearchResult("Object-oriented programming language");
    }

    @Test
    public void testCancelSearch() {
        SearchPageObject SearchPageObject = new SearchPageObject(driver);
        HintsPageObject HintPageObject = new HintsPageObject(driver);
        SearchPageObject.initSearchInput();

        HintPageObject.closeWelcomeOnboarding();

        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.clearSearchField();
        SearchPageObject.clickBackButton();
        SearchPageObject.waitForBackButtonToDisappear();
    }

    @Test
    public void testAmountofNotEmptySearch(){

        SearchPageObject SearchPageObject = new SearchPageObject(driver);
        HintsPageObject HintsPageObject = new HintsPageObject(driver);

        String search_line = "Vashkivtsi";

        SearchPageObject.initSearchInput();
        HintsPageObject.closeWelcomeOnboarding();
        SearchPageObject.typeSearchLine(search_line);
        int amount_of_search_results = SearchPageObject.getAmountOfFoundArticles();
        assertTrue(
                "We found too few results!",
                amount_of_search_results > 0
        );
    }

    @Test
    public void testAmountOfEmptySearch()
    {
        SearchPageObject SearchPageObject = new SearchPageObject(driver);
        HintsPageObject HintsPageObject = new HintsPageObject(driver);

        String search_line = "gggggggggggg";

        SearchPageObject.initSearchInput();
        HintsPageObject.closeWelcomeOnboarding();
        SearchPageObject.typeSearchLine(search_line);
        SearchPageObject.waitForEmptyResultsLabel();
        SearchPageObject.assertThereIsNoResultOfSearch();
    }




}
