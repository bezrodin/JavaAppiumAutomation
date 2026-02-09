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

    //Homework Lesson 3 Ex2 (Refactored in Lesson 5 Ex 8)
    @Test
    public void testAssertElementHasText() {
        SearchPageObject SearchPageObject = new SearchPageObject(driver);
        HintsPageObject HintsPageObject = new HintsPageObject(driver);
        SearchPageObject.initSearchInput();
        HintsPageObject.closeWelcomeOnboarding();
        String search_placeholder = "Search Wikipedia";
        SearchPageObject.assertCheckSearchPlaceholderText(search_placeholder);

    }

    //Homework Lesson 3 Ex3
    @Test
    public void testCheckResultAndCancelSearch() {
        SearchPageObject SearchPageObject = new SearchPageObject(driver);
        HintsPageObject HintsPageObject = new HintsPageObject(driver);

        String search_line = "Nokia";

        SearchPageObject.initSearchInput();

        //Закрываем онбординг, если он появился
        HintsPageObject.closeWelcomeOnboarding();

        //Ждем пока хинт над клавиатурой исчезнет
        SearchPageObject.waitForSnackBarToDisappear();

        SearchPageObject.typeSearchLine(search_line);

        //Проверяем, что список результатов отображается
        SearchPageObject.waitForSearchResultListPresent();

        //Т.к. в задании указано "Убеждается, что найдено несколько статей", проверим что поиск вернул несколько элементов массива.
        //Каждое изображение имеет свой индекс, по ним и выполним проверку
        String search_list_item_1 = "(//*[@resource-id='org.wikipedia:id/page_list_item_image'])[1]";
        String search_list_item_2 = "(//*[@resource-id='org.wikipedia:id/page_list_item_image'])[2]";

        SearchPageObject.waitForSearchListItem(search_list_item_1);

        //Если второй результат поиска имеет изображение, значит результат поиска состоит из нескольих статей
        SearchPageObject.waitForSearchListItem(search_list_item_2);

        //Нажимаем на кнопку очистки поля "Поиска"
        SearchPageObject.tapClearSearchFieldButton();

        //Проверяем, что список с результатами поиска не отображается
        SearchPageObject.waitForSearchResultListNotPresent();
    }

}
