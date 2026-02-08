import lib.CoreTestCase;
import lib.ui.*;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.*;
import java.time.Duration;

public class FirstTest extends CoreTestCase {
    private MainPageObject MainPageObject;
    protected void setUp() throws Exception
    {
      super.setUp();
      MainPageObject = new MainPageObject(driver);
    }

    @Test
    public void testSearch() {
        SearchPageObject SearchPageObject = new SearchPageObject(driver);
        SearchPageObject.initSearchInput();

        MainPageObject.clickIfElementPresent(
                By.xpath("//android.view.View[@content-desc='Close']"),
                "Cannot find 'Close onboarding' button",
                5
        );

        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.waitForSearchResult("Object-oriented programming language");
    }

    @Test
    public void testCancelSearch() {
        SearchPageObject SearchPageObject = new SearchPageObject(driver);
        SearchPageObject.initSearchInput();

        MainPageObject.clickIfElementPresent(
                By.xpath("//android.view.View[@content-desc='Close']"),
                "Cannot find 'Close onboarding' button",
                5
        );

        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.clearSearchField();
        SearchPageObject.clickBackButton();
        SearchPageObject.waitForBackButtonToDisappear();
    }

    @Test
    public void testCompareArticleTitle() {
        SearchPageObject SearchPageObject = new SearchPageObject(driver);
        SearchPageObject.initSearchInput();

        MainPageObject.clickIfElementPresent(
                By.xpath("//android.view.View[@content-desc='Close']"),
                "Cannot find 'Close onboarding' button",
                5
        );

        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.clickByArticleWithSubstring("Object-oriented programming language");

        MainPageObject.waitForElementAndClick(
                By.id("org.wikipedia:id/closeButton"),
                "Cannot find 'Close Wikipedia Games onboarding' button",
                5
        );

        ArticlePageObject ArticlePageObject = new ArticlePageObject(driver);
        ArticlePageObject.waitForContentsAndClick();
        String article_title = ArticlePageObject.getArticleTitle();

        Assert.assertEquals("We see unexpected title!",
                "Java (programming language)",
                article_title);

    }

    //Homework Lesson 3 Ex2
    @Test
    public void testAssertElementHasText() {

        MainPageObject.waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find 'Search Wikipedia' field",
                5
        );

        MainPageObject.clickIfElementPresent(
                By.xpath("//android.view.View[@content-desc='Close']"),
                "Cannot find 'Close onboarding' button",
                5
        );

        MainPageObject.assertElementHasText(
                By.id("org.wikipedia:id/search_src_text"),
                "Search Wikipedia",
                "Placeholder 'Search Wikipedia' not found"
        );

    }

    //Homework Lesson 3 Ex3
    @Test
    public void testCheckResultAndCancelSearch() {
        MainPageObject.waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find 'Search Wikipedia' field",
                5
        );

        //Закрываем онбординг, если он появился
        MainPageObject.clickIfElementPresent(
                By.xpath("//android.view.View[@content-desc='Close']"),
                "Cannot find 'Close onboarding' button",
                5
        );

        //Ждем пока хинт над клавиатурой исчезнет
        MainPageObject.waitForElementNotPresent(
                By.xpath("//*[@resource-id='org.wikipedia:id/snackbar_text' and @text='You can access your Year in Review later in the More menu.']"),
                "Cannot find 'Object-oriented programming language' description searching by 'Java'",
                15
        );


        MainPageObject.waitForElementAndSendKeys(
                By.id("org.wikipedia:id/search_src_text"),
                "Nokia",
                "Cannot find search input",
                5);

        //Проверяем, что список результатов отображается
        MainPageObject.waitForElementPresent(
                By.id("org.wikipedia:id/search_results_list"),
                "Cannot find search results",
                15
        );

        //Т.к. в задании указано "Убеждается, что найдено несколько статей", проверим что поиск вернул несколько элементов массива.
        //Каждое изображение имеет свой индекс, по ним и выполним проверку
        MainPageObject.waitForElementPresent(
                By.xpath("(//*[@resource-id='org.wikipedia:id/page_list_item_image'])[1]"),
                "Cannot find search results",
                15
        );

        //Если второй результат поиска имеет изображение, значит результат поиска состоит из нескольих статей
        MainPageObject.waitForElementPresent(
                By.xpath("(//*[@resource-id='org.wikipedia:id/page_list_item_image'])[2]"),
                "Cannot find search results",
                15
        );

        //Нажимаем на кнопку очистки поля "Поиска"
        MainPageObject.waitForElementAndClick(
                By.id("org.wikipedia:id/search_close_btn"),
                "Cannot find 'Clear Search field' button",
                5
        );

        //Проверяем, что список с результатами поиска не отображается
        MainPageObject.waitForElementNotPresent(
                By.id("org.wikipedia:id/search_results_list"),
                "Search Result still shows",
                5
        );
    }

    @Test
    public void testSwipeArticle() {
        SearchPageObject SearchPageObject = new SearchPageObject(driver);
        SearchPageObject.initSearchInput();

        MainPageObject.clickIfElementPresent(
                By.xpath("//android.view.View[@content-desc='Close']"),
                "Cannot find 'Close onboarding' button",
                5
        );

        SearchPageObject.typeSearchLine("Appium");
        SearchPageObject.clickByArticleWithSubstring("Automation for Apps");


        MainPageObject.waitForElementAndClick(
                By.id("org.wikipedia:id/closeButton"),
                "Cannot find 'Close Wikipedia Games onboarding' button",
                5
        );

        ArticlePageObject ArticlePageObject = new ArticlePageObject(driver);
        ArticlePageObject.waitForContentsButton();
        ArticlePageObject.swipeToFooter();
    }

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




    @Test
    public void testAmountofNotEmptySearch(){
        MainPageObject.waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find 'Search Wikipedia' field",
                5
        );


        MainPageObject.clickIfElementPresent(
                By.xpath("//android.view.View[@content-desc='Close']"),
                "Cannot find 'Close onboarding' button",
                5
        );

        String search_line = "Vashkivtsi";
        MainPageObject.waitForElementAndSendKeys(
                By.id("org.wikipedia:id/search_src_text"),
                search_line,
                "Cannot find search input",
                5);

        String search_result_locator = "//android.widget.TextView[@resource-id='org.wikipedia:id/page_list_item_title' and @text='Vashkivtsi']";
        MainPageObject.waitForElementPresent(
                By.xpath(search_result_locator),
                "Cannot find anything by the request " + search_line,
                15
        );

        int amount_of_search_results = MainPageObject.getAmountOfElements(
                By.xpath(search_result_locator)
        );

        Assert.assertTrue(
                "We found too few results!",
                amount_of_search_results > 0
        );
    }

    @Test
    public void testAmountOfEmptySearch()
    {
        MainPageObject.waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find 'Search Wikipedia' field",
                5
        );


        MainPageObject.clickIfElementPresent(
                By.xpath("//android.view.View[@content-desc='Close']"),
                "Cannot find 'Close onboarding' button",
                5
        );

        String search_line = "gggggggggggg";
        MainPageObject.waitForElementAndSendKeys(
                By.id("org.wikipedia:id/search_src_text"),
                search_line,
                "Cannot find search input",
                5);

        String search_result_locator = "//android.widget.TextView[@resource-id='org.wikipedia:id/page_list_item_title' and @text='Vashkivtsi']";
        String empty_result_label = "//*[@text='No results']";

        MainPageObject.waitForElementPresent(
                By.xpath(empty_result_label),
                "Cannot find empty result by the request " + search_line,
                15
        );

        MainPageObject.assertElementNotPresent(
                By.xpath(search_result_locator),
                "We've found some results by request " + search_line
        );

    }

    @Test
    public void testChangeScreenOrientationOnSearchResults(){
        MainPageObject.waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find 'Search Wikipedia' field",
                5
        );

        MainPageObject.clickIfElementPresent(
                By.xpath("//android.view.View[@content-desc='Close']"),
                "Cannot find 'Close onboarding' button",
                5
        );

        MainPageObject.waitForElementNotPresent(
                By.xpath("//*[@resource-id='org.wikipedia:id/snackbar_text' and @text='You can access your Year in Review later in the More menu.']"),
                "Cannot find 'Object-oriented programming language' description searching by 'Java'",
                15
        );


        String search_line = "Java";
        MainPageObject.waitForElementAndSendKeys(
                By.id("org.wikipedia:id/search_src_text"),
                search_line,
                "Cannot find search input",
                5);

        MainPageObject.waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_description' and @text='Object-oriented programming language']"),
                "Cannot find 'Object-oriented programming language' description searching by " + search_line,
                15
        );

        MainPageObject.clickIfElementPresent(
                By.id("org.wikipedia:id/closeButton"),
                "Cannot find 'Close Wikipedia Games onboarding' button",
                5
        );

        MainPageObject.clickIfElementPresent(
                By.xpath("//*[@text='Got it']"),
                "Cannot hide 'Customise your toolbar' dialog",
                5
        );

        String article_description = "//android.view.View[@resource-id='pcs-edit-section-title-description']";
        String title_before_rotation = MainPageObject.waitForElementAndGetAttribute(
                By.xpath(article_description),
                "text",
                "Cannot find title of article",
                5
        );

        driver.rotate(ScreenOrientation.LANDSCAPE);

        String title_after_rotation = MainPageObject.waitForElementAndGetAttribute(
                By.xpath(article_description),
                "text",
                "Cannot find title of article",
                5
        );

        Assert.assertEquals(
                "Article title have been changed after screen rotation",
                title_before_rotation,
                title_after_rotation
        );

        driver.rotate(ScreenOrientation.LANDSCAPE);

        String title_after_second_rotation = MainPageObject.waitForElementAndGetAttribute(
                By.xpath(article_description),
                "text",
                "Cannot find description of article",
                5
        );


        Assert.assertEquals(
                "Article title have been changed after screen rotation",
                title_before_rotation,
                title_after_second_rotation
        );


    }

    @Test
    public void testCheckSearchArticleInBackground(){
        MainPageObject.waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find 'Search Wikipedia' field",
                5
        );

        MainPageObject.clickIfElementPresent(
                By.xpath("//android.view.View[@content-desc='Close']"),
                "Cannot find 'Close onboarding' button",
                5
        );

        MainPageObject.waitForElementNotPresent(
                By.xpath("//*[@resource-id='org.wikipedia:id/snackbar_text' and @text='You can access your Year in Review later in the More menu.']"),
                "Cannot find 'Object-oriented programming language' description searching by 'Java'",
                15
        );


        String search_line = "Java";
        String second_title = "Object-oriented programming language";
        MainPageObject.waitForElementAndSendKeys(
                By.id("org.wikipedia:id/search_src_text"),
                search_line,
                "Cannot find search input",
                5);

        MainPageObject.waitForElementPresent(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_description' and @text='" + second_title + "']"),
                "Cannot find 'Object-oriented programming language' description searching by " + second_title,
                15
        );

        driver.runAppInBackground(Duration.ofSeconds(3));

        MainPageObject.waitForElementPresent(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_description' and @text='" + second_title + "']"),
                "Cannot find article after returning from background " + second_title,
                15
        );
    }

    //Homework Lesson 4 Ex5
    //Wikipedia APK version: r/50563-r-2025-12-11
    @Test
    public void testSaveTwoArticlesToMyListAndRemoveOne()
    {
        //Нажимаем на полу "Поиск"
        MainPageObject.waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find 'Search Wikipedia' field",
                5
        );

        //Если появляется онбординг, то закрываем его. Если нет, переходим к следующему шагу
        MainPageObject.clickIfElementPresent(
                By.xpath("//android.view.View[@content-desc='Close']"),
                "Cannot find 'Close onboarding' button",
                5
        );

        //Ожидаем что хинт исчез
        MainPageObject.waitForElementNotPresent(
                By.xpath("//*[@resource-id='org.wikipedia:id/snackbar_text' and @text='You can access your Year in Review later in the More menu.']"),
                "Cannot find 'Object-oriented programming language' description searching by 'Java'",
                15
        );

        //Вводим поисковый запрос
        String search_line = "Java";
        MainPageObject.waitForElementAndSendKeys(
                By.id("org.wikipedia:id/search_src_text"),
                search_line,
                "Cannot find search input",
                5);

        //Ищем результат поиска с подзаголовком 'Object-oriented programming language', т.к. Title не получается вытащить из статьи
        String search_result_title = "Object-oriented programming language";
        MainPageObject.waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_description' and @text='" + search_result_title + "']"),
                "Cannot find ' " + search_result_title + "' description searching by 'Java'",
                15
        );

        //Если после перехода на статью появляется сплеш с онбордингом, то закрываем его. Если нет, переходим к следующему шагу
        MainPageObject.clickIfElementPresent(
                By.id("org.wikipedia:id/closeButton"),
                "Cannot find 'Close Wikipedia Games onboarding' button",
                5
        );

        //Нажимаем на кнопку в таббаре добавления статьи
        MainPageObject.waitForElementAndClick(
                By.id("org.wikipedia:id/page_save"),
                "Cannot find 'Save page' button",
                15
        );

        //На появившемся хинте, выбираем создать список
        MainPageObject.waitForElementAndClick(
                By.id("org.wikipedia:id/snackbar_action"),
                "Cannot find snackbar action",
                15
        );


        //Если появляется bottom-sheet "Move to reading list" с кнопкой "Create new" нажимаем на нее
        MainPageObject.clickIfElementPresent(
                By.id("org.wikipedia:id/create_button"),
                "Cannot find Move to reading list snackbar",
                5
        );

        //Задаем название и описание списка
        String name_of_list = "Favorite";
        String description_of_list = "Read later";

        MainPageObject.waitForElementAndSendKeys(
                By.id("org.wikipedia:id/text_input"),
                name_of_list,
                "Cannot find 'Name of this list' field",
                5);

        MainPageObject.waitForElementAndSendKeys(
                By.id("org.wikipedia:id/secondary_text_input"),
                description_of_list,
                "Cannot find 'Name of this list' field",
                5);

        //Сохраняем введенные значения и создаем список
        MainPageObject.waitForElementAndClick(
                By.xpath("//android.widget.Button[@text='OK']"),
                "Cannot tap OK button",
                5
        );

        //Возвращаемся к экрану поиска
        MainPageObject.waitForElementAndClick(
                By.xpath("//*[@content-desc='Navigate up']"),
                "Unable return to leave article screen",
                5
        );

        //Очищаем поле поиск
        MainPageObject.waitForElementAndClick(
                By.id("org.wikipedia:id/search_close_btn"),
                "Unable clear Search field",
                5
        );

        //Проверяем что находимся на экране "Поиск"
        MainPageObject.waitForElementPresent(By.id("org.wikipedia:id/search_src_text"),
                "Cannot find search screen",
                5);

        //Вводим второй запрос на поиск
        String search_line_2 = "Python";
        MainPageObject.waitForElementAndSendKeys(
                By.id("org.wikipedia:id/search_src_text"),
                search_line_2,
                "Cannot find search input",
                5);

        //Переходим на статью о языке программирования Python c заданным подзаголовком
        String search_result_title_2 = "General-purpose programming language";
        MainPageObject.waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_description' and @text='" + search_result_title_2 + "']"),
                "Cannot find ' " + search_result_title_2 + "' description searching by '" + search_line_2 + "'",
                15
        );


        //Нажимаем на кнопку в таббаре добавления статьи
        MainPageObject.waitForElementAndClick(
                By.id("org.wikipedia:id/page_save"),
                "Cannot find 'Save page' button",
                15
        );

        //На появившемся хинте нажимаем добавить в список
        MainPageObject.waitForElementAndClick(
                By.id("org.wikipedia:id/snackbar_action"),
                "Cannot find snackbar action",
                15
        );

        //Ищем созданный ранее список и нажимаем на него
        MainPageObject.waitForElementAndClick(
                By.xpath("//android.widget.TextView[@resource-id='org.wikipedia:id/item_title' and @text='Favorite']"),
                "Unable to add article to the list",
                5
        );

        //Возвращаемся к экрану "Поиск"
        MainPageObject.waitForElementAndClick(
                By.xpath("//*[@content-desc='Navigate up']"),
                "Unable return to Search screen",
                5
        );

        //Если появляется диалог с предложением залогиниться для синхронизации списков, скрываем его
        MainPageObject.clickIfElementPresent(
                By.id("android:id/button2"),
                "Cannot hide 'Sync reading lists' dialog",
                5
        );

        //Возвращаемся к Главному экрану
        MainPageObject.waitForElementAndClick(
                By.xpath("//*[@content-desc='Navigate up']"),
                "Unable return to Main screen",
                5
        );

        //Переходим к экрану с сохраненными списками статей
        MainPageObject.waitForElementAndClick(
                By.id("org.wikipedia:id/nav_tab_reading_lists"),
                "Unable return to Main screen",
                5
        );

        //Ищем созданный ранее список и нажимаем на него
        MainPageObject.waitForElementAndClick(
                By.xpath("//android.widget.TextView[@resource-id='org.wikipedia:id/item_title' and @text='" + name_of_list +"']"),
                "Unable find saved list",
                5
        );

        //Закрываем Хинт "Got it" если он показался
        MainPageObject.clickIfElementPresent(
                By.xpath("//android.widget.Button[@text='Got it']"),
                "Cannot hide 'Sync reading lists' dialog",
                5
        );

        //Проверяем что вторая статья добавилась в список
        MainPageObject.waitForElementPresent(By.xpath("//android.widget.TextView[@text='" + search_result_title_2 + "']"),
                "Cannot find article title",
                5);

        //Удаляем вторую статью
        MainPageObject.swipeElementToLeft(
                By.xpath("//android.widget.TextView[@text='"+ search_result_title_2 +"']"),
                "Unable to swipe element",
                3
        );

        //Убеждается, что вторая статья удалилась
        MainPageObject.waitForElementNotPresent(By.xpath("//android.widget.TextView[@text='" + search_result_title_2 + "']"),
                "Cannot delete saved article",
                5);


        //Сохраняем в переменную подзаголовок со списка статей
        String title_in_the_list = MainPageObject.waitForElementAndGetAttribute(
                By.xpath("//android.widget.TextView[@resource-id='org.wikipedia:id/page_list_item_description']"),
                "text",
                "Cannot find title of article",
                5
        );

        //Убеждаемся, что первая статья осталась и открываем ее
        MainPageObject.waitForElementAndClick(
                By.xpath("//android.widget.TextView[@text='" + search_result_title + "']"),
                "Unable find '" + search_result_title + "' article",
                5
        );

        //Сохраняем в переменную подзаголовок со страницы статьи
        String title_after_opening = MainPageObject.waitForElementAndGetAttribute(
                By.xpath("//android.view.View[@resource-id='pcs-edit-section-title-description']"),
                "text",
                "Cannot find title of article",
                5
        );

        //Сравниваем что подзаголовок статьи в списке совпадает с подзаголовком на странице статьи
        Assert.assertEquals(
                "Article titles are mismatched",
                title_in_the_list,
                title_after_opening
        );

    }


    //Homework Lesson4 Ex6
    @Test
    public void testAssertTitle()
    {
        //Нажимаем на полу "Поиск"
        MainPageObject.waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find 'Search Wikipedia' field",
                5
        );

        //Если появляется онбординг, то закрываем его. Если нет, переходим к следующему шагу
        MainPageObject.clickIfElementPresent(
                By.xpath("//android.view.View[@content-desc='Close']"),
                "Cannot find 'Close onboarding' button",
                5
        );

        //Ожидаем что хинт исчез
        MainPageObject.waitForElementNotPresent(
                By.xpath("//*[@resource-id='org.wikipedia:id/snackbar_text' and @text='You can access your Year in Review later in the More menu.']"),
                "Suppose user sees snack bar with text 'You can access your Year in Review later in the More menu.'",
                5
        );

        //Вводим поисковый запрос
        String search_line = "Java";
        MainPageObject.waitForElementAndSendKeys(
                By.id("org.wikipedia:id/search_src_text"),
                search_line,
                "Cannot find search input",
                5);

        //Ищем результат поиска с подзаголовком 'Object-oriented programming language', т.к. Title не получается вытащить из статьи
        String search_result_title = "Object-oriented programming language";
        MainPageObject.waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_description' and @text='" + search_result_title + "']"),
                "Cannot find ' " + search_result_title + "' description searching by 'Java'",
                15
        );

        //Если после перехода на статью появляется сплеш с онбордингом, то закрываем его. Если нет, переходим к следующему шагу
        MainPageObject.clickIfElementPresent(
                By.id("org.wikipedia:id/closeButton"),
                "Cannot find 'Close Wikipedia Games onboarding' button",
                5
        );

        //Сохраняем в переменную xpath подзаголовка
        String title_locator = "//*[@resource-id='pcs-edit-section-title-description']";

        /*
        //Код закомментирован, т.к. использовался для проверки, что assertElementPresent не валится с ошибкой, если заголовок действительно пришел.
        //Если не ждать появления элемента, то assertElementPresent присылает ошибку, что элемент не найден.

        waitForElementPresent(By.xpath(title_locator),
                "Title not found",
                15);
         */

        //Проверяем пришел ли подзаголовок
        MainPageObject.assertElementPresent(
                By.xpath(title_locator),
                "Cannot find title of article");



    }


}