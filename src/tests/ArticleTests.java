package tests;

import lib.CoreTestCase;
import lib.ui.*;
import org.junit.Test;

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

    //Homework Lesson4 Ex6 (Refactored in Lesson 5 Ex 8)
    @Test
    public void testAssertTitle()
    {
        SearchPageObject SearchPageObject = new SearchPageObject(driver);
        ArticlePageObject ArticlePageObject = new ArticlePageObject(driver);
        HintsPageObject HintsPageObject = new HintsPageObject(driver);

        //Нажимаем на полe "Поиск"
        SearchPageObject.initSearchInput();

        //Если появляется онбординг, то закрываем его. Если нет, переходим к следующему шагу
        HintsPageObject.closeWelcomeOnboarding();

        //Ожидаем что хинт исчез
        SearchPageObject.waitForSnackBarToDisappear();

        //Вводим поисковый запрос
        SearchPageObject.typeSearchLine("Java");

        //Ищем результат поиска с подзаголовком 'Object-oriented programming language', т.к. Title не получается вытащить из статьи
        SearchPageObject.clickByArticleWithSubstring("Object-oriented programming language");

        //Если после перехода на статью появляется сплеш с онбордингом, то закрываем его. Если нет, переходим к следующему шагу
        HintsPageObject.tapCloseWikipediaGamesOnboarding();

        //Сохраняем в переменную xpath подзаголовка
        String title_locator = "//*[@resource-id='pcs-edit-section-title-description']";

        //Проверяем пришел ли подзаголовок
        ArticlePageObject.assertArticleDescriptionPresent();

    }

}
