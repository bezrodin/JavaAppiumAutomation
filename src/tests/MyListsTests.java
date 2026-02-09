package tests;

import lib.CoreTestCase;
import lib.ui.*;
import org.junit.Test;
import org.openqa.selenium.By;

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


    //Homework Lesson 4 Ex5 (Refactored in Lesson 5 Ex 8)
    //Wikipedia APK version: r/50563-r-2025-12-11
    @Test
    public void testSaveTwoArticlesToMyListAndRemoveOne()
    {
        SearchPageObject SearchPageObject = new SearchPageObject(driver);
        ArticlePageObject ArticlePageObject = new ArticlePageObject(driver);
        HintsPageObject HintsPageObject = new HintsPageObject(driver);
        NavigationUI NavigationUI = new NavigationUI(driver);
        MyListsPageObject MyListsPageObject  = new MyListsPageObject(driver);

        //Нажимаем на поле "Поиск"
        SearchPageObject.initSearchInput();

        //Если появляется онбординг, то закрываем его. Если нет, переходим к следующему шагу
        HintsPageObject.closeWelcomeOnboarding();

        //Ожидаем что хинт исчез
        SearchPageObject.waitForSnackBarToDisappear();

        //Вводим поисковый запрос
        SearchPageObject.typeSearchLine("Java");

        //Ищем результат поиска с подзаголовком 'Object-oriented programming language', т.к. Title не получается вытащить из статьи
        String search_result_description_1 = "Object-oriented programming language";
        SearchPageObject.clickByArticleWithSubstring(search_result_description_1);

        //Если после перехода на статью появляется сплеш с онбордингом, то закрываем его. Если нет, переходим к следующему шагу
        HintsPageObject.tapCloseWikipediaGamesOnboarding();



        //Нажимаем на кнопку в таббаре добавления статьи
        ArticlePageObject.waitForSaveButtonAndClick();

        //На появившемся хинте, выбираем создать список
        ArticlePageObject.waitForSnackBarActionAndClick();


        //Если появляется bottom-sheet "Move to reading list" с кнопкой "Create new" нажимаем на нее
        HintsPageObject.tapMoveToReadingListHint();

        //Задаем название и описание списка
        String name_of_folder = "Favorite";
        String name_of_description = "Read Later";

        //Сохраняем введенные значения и создаем список
        ArticlePageObject.addArticleToMyList(
                name_of_folder,
                name_of_description
        );

        //Возвращаемся к экрану поиска
        NavigationUI.tapBackButton();

        //Очищаем поле поиск
        SearchPageObject.tapClearSearchFieldButton();


        //Проверяем что находимся на экране "Поиск"
        SearchPageObject.waitForSearchFieldPresent();

        //Вводим второй запрос на поиск
        SearchPageObject.typeSearchLine("Python");

        //Переходим на статью о языке программирования Python c заданным подзаголовком
        String search_result_description_2 = "General-purpose programming language";
        SearchPageObject.clickByArticleWithSubstring(search_result_description_2);


        //Нажимаем на кнопку в таббаре добавления статьи
        ArticlePageObject.waitForSaveButtonAndClick();

        //На появившемся хинте нажимаем добавить в список
        ArticlePageObject.waitForSnackBarActionAndClick();


        //Ищем созданный ранее список и нажимаем на него
        MyListsPageObject.openFolderByName(name_of_folder);

        //Возвращаемся к экрану "Поиск"
        NavigationUI.tapBackButton();

        //Если появляется диалог с предложением залогиниться для синхронизации списков, скрываем его
        HintsPageObject.hideSyncReadingListsDialog();

        //Возвращаемся к Главному экрану
        NavigationUI.tapBackButton();

        //Переходим к экрану с сохраненными списками статей
        NavigationUI.tapNavTabReadingLists();

        //Ищем созданный ранее список и нажимаем на него
        MyListsPageObject.openFolderByName(name_of_folder);

        //Закрываем Хинт "Got it" если он показался
        HintsPageObject.tapGotItButton();

        //Проверяем что вторая статья добавилась в список
        MyListsPageObject.waitForArticleToAppearByDescription(search_result_description_2);

        //Удаляем вторую статью
        MyListsPageObject.swipeByArticleDescriptionToDelete(search_result_description_2);

        //Убеждается, что вторая статья удалилась
        MyListsPageObject.waitForArticleToDisappearByDescription(search_result_description_2);

        //Сохраняем в переменную подзаголовок со списка статей
        String title_in_the_list = MyListsPageObject.getArticleDescription();

        //Убеждаемся, что первая статья осталась и открываем ее
        MyListsPageObject.waitForArticleByDescriptionAndClick(search_result_description_1);

        //Сохраняем в переменную подзаголовок со страницы статьи
        String title_after_opening = ArticlePageObject.getArticleDescription();

        //Сравниваем что подзаголовок статьи в списке совпадает с подзаголовком на странице статьи
        assertEquals(
                "Article titles are mismatched",
                title_in_the_list,
                title_after_opening
        );

    }

}
