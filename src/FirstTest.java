import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;

public class FirstTest {
    private AppiumDriver driver;

    @Before
    public void setUp() throws Exception {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("deviceName", "AndroidTestDevice");
        capabilities.setCapability("platformVersion", "9");
        capabilities.setCapability("automationName", "UiAutomator2");
        capabilities.setCapability("appPackage", "org.wikipedia");
        capabilities.setCapability("appActivity", ".main.MainActivity");
        capabilities.setCapability("app", "/Users/victor/Documents/Projects/JavaAppiumAutomation/JavaAppiumAutomation/apks/org.wikipedia.apk");

        driver = new AndroidDriver(new URL("http://localhost:4723/"), capabilities);

        WebElement element_skip = driver.findElementById("org.wikipedia:id/fragment_onboarding_skip_button");
        element_skip.click();
    }

    @After
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void firstTest() {
        waitForElementAndClick(
                By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "Cannot find search field",
                5
        );

        clickIfElementPresent(
                By.xpath("//android.view.View[@content-desc='Close']"),
                "Cannot find 'Close onboarding' button",
                5
        );

        waitForElementAndSendKeys(
                By.id("org.wikipedia:id/search_src_text"),
                "Java",
                "Cannot find search input",
                15

        );

        waitForElementPresent(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_description' and @text='Object-oriented programming language']"),
                "Cannot find 'Object-oriented programming language' topic searching by 'Java'",
                15
        );

    }

    @Test
    public void testCancelSearch() {
        waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find 'Search Wikipedia' field",
                5
        );

        clickIfElementPresent(
                By.xpath("//android.view.View[@content-desc='Close']"),
                "Cannot find 'Close onboarding' button",
                5
        );

        waitForElementAndSendKeys(
                By.id("org.wikipedia:id/search_src_text"),
                "Java",
                "Cannot find search input",
                5);

        waitForElementAndClear(
                By.id("org.wikipedia:id/search_src_text"),
                "Cannot find search field",
                5
        );

        waitForElementAndClick(
                By.xpath("//*[@content-desc='Navigate up']"),
                "Cannot find 'Back' to leave search",
                5
        );


        waitForElementNotPresent(
                By.id("org.wikipedia:id/search_src_text"),
                "User is stiil on the Search page",
                5
        );
    }

    @Test
    public void testCompareArticleTitle() {
        waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find 'Search Wikipedia' field",
                5
        );

        clickIfElementPresent(
                By.xpath("//android.view.View[@content-desc='Close']"),
                "Cannot find 'Close onboarding' button",
                5
        );

        waitForElementNotPresent(
                By.xpath("//*[@resource-id='org.wikipedia:id/snackbar_text' and @text='You can access your Year in Review later in the More menu.']"),
                "Cannot find 'Object-oriented programming language' description searching by 'Java'",
                15
        );


        waitForElementAndSendKeys(
                By.id("org.wikipedia:id/search_src_text"),
                "Java",
                "Cannot find search input",
                5);

        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_description' and @text='Object-oriented programming language']"),
                "Cannot find 'Object-oriented programming language' description searching by 'Java'",
                15
        );

        waitForElementAndClick(
                By.id("org.wikipedia:id/closeButton"),
                "Cannot find 'Close Wikipedia Games onboarding' button",
                5
        );

        waitForElementAndClick(
                By.id("org.wikipedia:id/page_contents"),
                "Cannot find 'Contents' button",
                5
        );

        WebElement title_element = waitForElementPresent(
                By.id("org.wikipedia:id/page_toc_item_text"),
                "Cannot find article title",
                15
        );

        String article_title = title_element.getAttribute("text");

        Assert.assertEquals("We see unexpected title!",
                "Java (programming language)",
                article_title);

    }

    //Homework Lesson 3 Ex2
    @Test
    public void testAssertElementHasText() {
        waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find 'Search Wikipedia' field",
                5
        );

        clickIfElementPresent(
                By.xpath("//android.view.View[@content-desc='Close']"),
                "Cannot find 'Close onboarding' button",
                5
        );

        assertElementHasText(
                By.id("org.wikipedia:id/search_src_text"),
                "Search Wikipedia",
                "Placeholder 'Search Wikipedia' not found"
        );

    }

    //Homework Lesson 3 Ex3
    @Test
    public void testCheckResultAndCancelSearch() {
        waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find 'Search Wikipedia' field",
                5
        );

        //Закрываем онбординг, если он появился
        clickIfElementPresent(
                By.xpath("//android.view.View[@content-desc='Close']"),
                "Cannot find 'Close onboarding' button",
                5
        );

        //Ждем пока хинт над клавиатурой исчезнет
        waitForElementNotPresent(
                By.xpath("//*[@resource-id='org.wikipedia:id/snackbar_text' and @text='You can access your Year in Review later in the More menu.']"),
                "Cannot find 'Object-oriented programming language' description searching by 'Java'",
                15
        );


        waitForElementAndSendKeys(
                By.id("org.wikipedia:id/search_src_text"),
                "Nokia",
                "Cannot find search input",
                5);

        //Проверяем, что список результатов отображается
        waitForElementPresent(
                By.id("org.wikipedia:id/search_results_list"),
                "Cannot find search results",
                15
        );

        //Т.к. в задании указано "Убеждается, что найдено несколько статей", проверим что поиск вернул несколько элементов массива.
        //Каждое изображение имеет свой индекс, по ним и выполним проверку
        waitForElementPresent(
                By.xpath("(//*[@resource-id='org.wikipedia:id/page_list_item_image'])[1]"),
                "Cannot find search results",
                15
        );

        //Если второй результат поиска имеет изображение, значит результат поиска состоит из нескольих статей
        waitForElementPresent(
                By.xpath("(//*[@resource-id='org.wikipedia:id/page_list_item_image'])[2]"),
                "Cannot find search results",
                15
        );

        //Нажимаем на кнопку очистки поля "Поиска"
        waitForElementAndClick(
                By.id("org.wikipedia:id/search_close_btn"),
                "Cannot find 'Clear Search field' button",
                5
        );

        //Проверяем, что список с результатами поиска не отображается
        waitForElementNotPresent(
                By.id("org.wikipedia:id/search_results_list"),
                "Search Result still shows",
                5
        );
    }

    @Test
    public void testSwipeArticle() {
        waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find 'Search Wikipedia' field",
                5
        );


        clickIfElementPresent(
                By.xpath("//android.view.View[@content-desc='Close']"),
                "Cannot find 'Close onboarding' button",
                5
        );

        waitForElementNotPresent(
                By.xpath("//*[@resource-id='org.wikipedia:id/snackbar_text' and @text='You can access your Year in Review later in the More menu.']"),
                "Cannot find 'Object-oriented programming language' description searching by 'Java'",
                15
        );


        waitForElementAndSendKeys(
                By.id("org.wikipedia:id/search_src_text"),
                "Appium",
                "Cannot find search input",
                5);

        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_description' and @text='Automation for Apps']"),
                "Cannot find 'Automation for Apps' description searching by 'Appium'",
                15
        );

        waitForElementAndClick(
                By.id("org.wikipedia:id/closeButton"),
                "Cannot find 'Close Wikipedia Games onboarding' button",
                5
        );

        swipeUpToFindElement(
                By.xpath("//*[@resource-id='pcs-footer-container-legal']"),
                "Cannot find the end of the article",
                20);


    }

    @Test
    public void saveFirstArticleToMyList()
    {
        waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find 'Search Wikipedia' field",
                5
        );

        clickIfElementPresent(
                By.xpath("//android.view.View[@content-desc='Close']"),
                "Cannot find 'Close onboarding' button",
                5
        );

        waitForElementNotPresent(
                By.xpath("//*[@resource-id='org.wikipedia:id/snackbar_text' and @text='You can access your Year in Review later in the More menu.']"),
                "Cannot find 'Object-oriented programming language' description searching by 'Java'",
                15
        );


        waitForElementAndSendKeys(
                By.id("org.wikipedia:id/search_src_text"),
                "Java",
                "Cannot find search input",
                5);

        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_description' and @text='Object-oriented programming language']"),
                "Cannot find 'Object-oriented programming language' description searching by 'Java'",
                15
        );

        clickIfElementPresent(
                By.id("org.wikipedia:id/closeButton"),
                "Cannot find 'Close Wikipedia Games onboarding' button",
                15
        );

        waitForElementAndClick(
                By.id("org.wikipedia:id/page_save"),
                "Cannot find 'Save page' button",
                15
        );

        waitForElementAndClick(
                By.id("org.wikipedia:id/snackbar_action"),
                "Cannot find snackbar action",
                15
        );

/*
        waitForElementAndClick(
                By.id("org.wikipedia:id/create_button"),
                "Cannot find snackbar action",
                15
        );

 */
        String name_of_folder = "Favorite";

        waitForElementAndSendKeys(
                By.id("org.wikipedia:id/text_input"),
                name_of_folder,
                "Cannot find 'Name of this list' field",
                5);

        waitForElementAndSendKeys(
                By.id("org.wikipedia:id/secondary_text_input"),
                "Read later",
                "Cannot find 'Name of this list' field",
                5);


        waitForElementAndClick(
                By.id("android:id/button1"),
                "Cannot tap OK button",
                5
        );

        waitForElementAndClick(
                By.xpath("//*[@content-desc='Navigate up']"),
                "Unable return to leave article screen",
                5
        );

        waitForElementPresent(By.id("org.wikipedia:id/search_src_text"),
                "Cannot find search screen",
                5);

        waitForElementAndClick(
                By.xpath("//*[@content-desc='Navigate up']"),
                "Unable return to Main screen",
                5
        );

        clickIfElementPresent(
                By.id("android:id/button2"),
                "Cannot hide 'Sync reading lists' dialog",
                15
        );


        waitForElementAndClick(
                By.id("org.wikipedia:id/nav_tab_reading_lists"),
                "Unable return to Main screen",
                5
        );

        waitForElementAndClick(
                By.xpath("//android.widget.TextView[@resource-id='org.wikipedia:id/item_title' and @text='" + name_of_folder +"']"),
                "Unable find saved list",
                5
        );

        clickIfElementPresent(
                By.id("org.wikipedia:id/buttonView"),
                "Cannot hide 'Sync reading lists' dialog",
                5
        );

        clickIfElementPresent(
                By.xpath("//android.widget.Button[@text='Got it']"),
                "Cannot hide 'Sync reading lists' dialog",
                5
        );

        waitForElementPresent(By.xpath("//android.widget.TextView[@text='Java (programming language)']"),
                "Cannot find article title",
                5);

        swipeElementToLeft(
                By.xpath("//android.widget.TextView[@text='Java (programming language)']"),
                "Unable to swipe element",
                3
        );

        waitForElementNotPresent(By.xpath("//android.widget.TextView[@text='Java (programming language)']"),
                "Cannot delete saved article",
                5);

    }


    @Test
    public void testAmountofNotEmptySearch(){
        waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find 'Search Wikipedia' field",
                5
        );


        clickIfElementPresent(
                By.xpath("//android.view.View[@content-desc='Close']"),
                "Cannot find 'Close onboarding' button",
                5
        );

        String search_line = "Vashkivtsi";
        waitForElementAndSendKeys(
                By.id("org.wikipedia:id/search_src_text"),
                search_line,
                "Cannot find search input",
                5);

        String search_result_locator = "//android.widget.TextView[@resource-id='org.wikipedia:id/page_list_item_title' and @text='Vashkivtsi']";
        waitForElementPresent(
                By.xpath(search_result_locator),
                "Cannot find anything by the request " + search_line,
                15
        );

        int amount_of_search_results = getAmountOfElements(
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
        waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find 'Search Wikipedia' field",
                5
        );


        clickIfElementPresent(
                By.xpath("//android.view.View[@content-desc='Close']"),
                "Cannot find 'Close onboarding' button",
                5
        );

        String search_line = "gggggggggggg";
        waitForElementAndSendKeys(
                By.id("org.wikipedia:id/search_src_text"),
                search_line,
                "Cannot find search input",
                5);

        String search_result_locator = "//android.widget.TextView[@resource-id='org.wikipedia:id/page_list_item_title' and @text='Vashkivtsi']";
        String empty_result_label = "//*[@text='No results']";

        waitForElementPresent(
                By.xpath(empty_result_label),
                "Cannot find empty result by the request " + search_line,
                15
        );

        assertElementNotPresent(
                By.xpath(search_result_locator),
                "We've found some results by request " + search_line
        );

    }

    @Test
    public void testChangeScreenOrientationOnSearchResults(){
        waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find 'Search Wikipedia' field",
                5
        );

        clickIfElementPresent(
                By.xpath("//android.view.View[@content-desc='Close']"),
                "Cannot find 'Close onboarding' button",
                5
        );

        waitForElementNotPresent(
                By.xpath("//*[@resource-id='org.wikipedia:id/snackbar_text' and @text='You can access your Year in Review later in the More menu.']"),
                "Cannot find 'Object-oriented programming language' description searching by 'Java'",
                15
        );


        String search_line = "Java";
        waitForElementAndSendKeys(
                By.id("org.wikipedia:id/search_src_text"),
                search_line,
                "Cannot find search input",
                5);

        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_description' and @text='Object-oriented programming language']"),
                "Cannot find 'Object-oriented programming language' description searching by " + search_line,
                15
        );

        clickIfElementPresent(
                By.id("org.wikipedia:id/closeButton"),
                "Cannot find 'Close Wikipedia Games onboarding' button",
                5
        );

        clickIfElementPresent(
                By.xpath("//*[@text='Got it']"),
                "Cannot hide 'Customise your toolbar' dialog",
                5
        );

        String article_description = "//android.view.View[@resource-id='pcs-edit-section-title-description']";
        String title_before_rotation = waitForElementAndGetAttribute(
                By.xpath(article_description),
                "text",
                "Cannot find title of article",
                5
        );

        driver.rotate(ScreenOrientation.LANDSCAPE);

        String title_after_rotation = waitForElementAndGetAttribute(
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

        String title_after_second_rotation = waitForElementAndGetAttribute(
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
        waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find 'Search Wikipedia' field",
                5
        );

        clickIfElementPresent(
                By.xpath("//android.view.View[@content-desc='Close']"),
                "Cannot find 'Close onboarding' button",
                5
        );

        waitForElementNotPresent(
                By.xpath("//*[@resource-id='org.wikipedia:id/snackbar_text' and @text='You can access your Year in Review later in the More menu.']"),
                "Cannot find 'Object-oriented programming language' description searching by 'Java'",
                15
        );


        String search_line = "Java";
        String second_title = "Object-oriented programming language";
        waitForElementAndSendKeys(
                By.id("org.wikipedia:id/search_src_text"),
                search_line,
                "Cannot find search input",
                5);

        waitForElementPresent(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_description' and @text='" + second_title + "']"),
                "Cannot find 'Object-oriented programming language' description searching by " + second_title,
                15
        );

        driver.runAppInBackground(Duration.ofSeconds(3));

        waitForElementPresent(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_description' and @text='" + second_title + "']"),
                "Cannot find article after returning from background " + second_title,
                15
        );
    }

    //Homework Lesson 4 Ex5
    //Wikipedia APK version: r/50563-r-2025-12-11
    @Test
    public void saveTwoArticlesToMyListAndRemoveOne()
    {
        //Нажимаем на полу "Поиск"
        waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find 'Search Wikipedia' field",
                5
        );

        //Если появляется онбординг, то закрываем его. Если нет, переходим к следующему шагу
        clickIfElementPresent(
                By.xpath("//android.view.View[@content-desc='Close']"),
                "Cannot find 'Close onboarding' button",
                5
        );

        //Ожидаем что хинт исчез
        waitForElementNotPresent(
                By.xpath("//*[@resource-id='org.wikipedia:id/snackbar_text' and @text='You can access your Year in Review later in the More menu.']"),
                "Cannot find 'Object-oriented programming language' description searching by 'Java'",
                15
        );

        //Вводим поисковый запрос
        String search_line = "Java";
        waitForElementAndSendKeys(
                By.id("org.wikipedia:id/search_src_text"),
                search_line,
                "Cannot find search input",
                5);

        //Ищем результат поиска с подзаголовком 'Object-oriented programming language', т.к. Title не получается вытащить из статьи
        String search_result_title = "Object-oriented programming language";
        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_description' and @text='" + search_result_title + "']"),
                "Cannot find ' " + search_result_title + "' description searching by 'Java'",
                15
        );

        //Если после перехода на статью появляется сплеш с онбордингом, то закрываем его. Если нет, переходим к следующему шагу
        clickIfElementPresent(
                By.id("org.wikipedia:id/closeButton"),
                "Cannot find 'Close Wikipedia Games onboarding' button",
                5
        );

        //Нажимаем на кнопку в таббаре добавления статьи
        waitForElementAndClick(
                By.id("org.wikipedia:id/page_save"),
                "Cannot find 'Save page' button",
                15
        );

        //На появившемся хинте, выбираем создать список
        waitForElementAndClick(
                By.id("org.wikipedia:id/snackbar_action"),
                "Cannot find snackbar action",
                15
        );


        //Если появляется bottom-sheet "Move to reading list" с кнопкой "Create new" нажимаем на нее
        clickIfElementPresent(
                By.id("org.wikipedia:id/create_button"),
                "Cannot find Move to reading list snackbar",
                5
        );

        //Задаем название и описание списка
        String name_of_list = "Favorite";
        String description_of_list = "Read later";

        waitForElementAndSendKeys(
                By.id("org.wikipedia:id/text_input"),
                name_of_list,
                "Cannot find 'Name of this list' field",
                5);

        waitForElementAndSendKeys(
                By.id("org.wikipedia:id/secondary_text_input"),
                description_of_list,
                "Cannot find 'Name of this list' field",
                5);

        //Сохраняем введенные значения и создаем список
        waitForElementAndClick(
                By.xpath("//android.widget.Button[@text='OK']"),
                "Cannot tap OK button",
                5
        );

        //Возвращаемся к экрану поиска
        waitForElementAndClick(
                By.xpath("//*[@content-desc='Navigate up']"),
                "Unable return to leave article screen",
                5
        );

        //Очищаем поле поиск
        waitForElementAndClick(
                By.id("org.wikipedia:id/search_close_btn"),
                "Unable clear Search field",
                5
        );

        //Проверяем что находимся на экране "Поиск"
        waitForElementPresent(By.id("org.wikipedia:id/search_src_text"),
                "Cannot find search screen",
                5);

        //Вводим второй запрос на поиск
        String search_line_2 = "Python";
        waitForElementAndSendKeys(
                By.id("org.wikipedia:id/search_src_text"),
                search_line_2,
                "Cannot find search input",
                5);

        //Переходим на статью о языке программирования Python c заданным подзаголовком
        String search_result_title_2 = "General-purpose programming language";
        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_description' and @text='" + search_result_title_2 + "']"),
                "Cannot find ' " + search_result_title_2 + "' description searching by '" + search_line_2 + "'",
                15
        );


        //Нажимаем на кнопку в таббаре добавления статьи
        waitForElementAndClick(
                By.id("org.wikipedia:id/page_save"),
                "Cannot find 'Save page' button",
                15
        );

        //На появившемся хинте нажимаем добавить в список
        waitForElementAndClick(
                By.id("org.wikipedia:id/snackbar_action"),
                "Cannot find snackbar action",
                15
        );

        //Ищем созданный ранее список и нажимаем на него
        waitForElementAndClick(
                By.xpath("//android.widget.TextView[@resource-id='org.wikipedia:id/item_title' and @text='Favorite']"),
                "Unable to add article to the list",
                5
        );

        //Возвращаемся к экрану "Поиск"
        waitForElementAndClick(
                By.xpath("//*[@content-desc='Navigate up']"),
                "Unable return to Search screen",
                5
        );

        //Если появляется диалог с предложением залогиниться для синхронизации списков, скрываем его
        clickIfElementPresent(
                By.id("android:id/button2"),
                "Cannot hide 'Sync reading lists' dialog",
                5
        );

        //Возвращаемся к Главному экрану
        waitForElementAndClick(
                By.xpath("//*[@content-desc='Navigate up']"),
                "Unable return to Main screen",
                5
        );

        //Переходим к экрану с сохраненными списками статей
        waitForElementAndClick(
                By.id("org.wikipedia:id/nav_tab_reading_lists"),
                "Unable return to Main screen",
                5
        );

        //Ищем созданный ранее список и нажимаем на него
        waitForElementAndClick(
                By.xpath("//android.widget.TextView[@resource-id='org.wikipedia:id/item_title' and @text='" + name_of_list +"']"),
                "Unable find saved list",
                5
        );

        //Закрываем Хинт "Got it" если он показался
        clickIfElementPresent(
                By.xpath("//android.widget.Button[@text='Got it']"),
                "Cannot hide 'Sync reading lists' dialog",
                5
        );

        //Проверяем что вторая статья добавилась в список
        waitForElementPresent(By.xpath("//android.widget.TextView[@text='" + search_result_title_2 + "']"),
                "Cannot find article title",
                5);

        //Удаляем вторую статью
        swipeElementToLeft(
                By.xpath("//android.widget.TextView[@text='"+ search_result_title_2 +"']"),
                "Unable to swipe element",
                3
        );

        //Убеждается, что вторая статья удалилась
        waitForElementNotPresent(By.xpath("//android.widget.TextView[@text='" + search_result_title_2 + "']"),
                "Cannot delete saved article",
                5);


        //Сохраняем в переменную подзаголовок со списка статей
        String title_in_the_list = waitForElementAndGetAttribute(
                By.xpath("//android.widget.TextView[@resource-id='org.wikipedia:id/page_list_item_description']"),
                "text",
                "Cannot find title of article",
                5
        );

        //Убеждаемся, что первая статья осталась и открываем ее
        waitForElementAndClick(
                By.xpath("//android.widget.TextView[@text='" + search_result_title + "']"),
                "Unable find '" + search_result_title + "' article",
                5
        );

        //Сохраняем в переменную подзаголовок со страницы статьи
        String title_after_opening = waitForElementAndGetAttribute(
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


    private WebElement waitForElementPresent(By by, String error_message, long timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(error_message + "\n");
        return wait.until(
                ExpectedConditions.presenceOfElementLocated(by)
        );
    }

    private WebElement waitForElementPresent(By by, String error_message) {
        return waitForElementPresent(by, error_message, 5);
    }

    private WebElement waitForElementAndClick(By by, String error_message, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(by, error_message, 5);
        element.click();
        return element;
    }

    private WebElement waitForElementAndSendKeys(By by, String value, String error_message, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(by, error_message, 15);
        element.sendKeys(value);
        return element;
    }

    private boolean waitForElementNotPresent(By by, String error_message, long timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(error_message + "\n");
        return wait.until(
                ExpectedConditions.invisibilityOfElementLocated(by)
        );
    }

    private WebElement waitForElementAndClear(By by, String error_message, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(by, error_message, timeoutInSeconds);
        element.clear();
        return element;
    }

    //Homework Lesson 3 Ex2
    private WebElement assertElementHasText(By by, String expected_text, String error_message) {
        WebElement element = waitForElementPresent(by, error_message);
        String element_text = element.getAttribute("text");
        Assert.assertEquals(error_message, expected_text, element_text);
        return element;
    }


    protected void swipeUp(int timeOfSwipe)
    {
        Dimension size = driver.manage().window().getSize();
        int startY = (int) (size.height * 0.70);
        int endY = (int) (size.height * 0.30);
        int centerX = size.width / 2;

        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH,"finger");
        Sequence swipe = new Sequence(finger,1)

                //Двигаем палец на начальную позицию
                .addAction(finger.createPointerMove(Duration.ofSeconds(0),
                        PointerInput.Origin.viewport(), centerX, startY))
                //Палец прикасается к экрану
                .addAction(finger.createPointerDown(0))

                //Палец двигается к конечной точке
                .addAction(finger.createPointerMove(Duration.ofMillis(timeOfSwipe),
                        PointerInput.Origin.viewport(), centerX, endY))

                //Убираем палец с экрана
                .addAction(finger.createPointerUp(0));

        //Выполняем действия
        driver.perform(Arrays.asList(swipe));
    }

    protected void swipeUpQuick(){
        swipeUp(200);
    }

    protected void swipeUpToFindElement(By by, String error_message, int max_swipes){
        int already_swiped = 0;
        while (driver.findElements(by).size() == 0){
            if (already_swiped > max_swipes){
                waitForElementPresent(
                        by,
                        "Cannot find element by swiping up. \n" + error_message,
                        0);
                return;

            }

            swipeUpQuick();
            ++already_swiped;
            System.out.println("Already swiped: "+ already_swiped + " from " + max_swipes);

        }

    }

    protected boolean isElementPresent(By by) {
        //return driver.findElements(by).size() > 0;
        return isElementPresent(by, 5); // По умолчанию 5 секунд ожидания
        }

    protected boolean isElementPresent(By by, int timeoutSeconds) {
        long startTime = System.currentTimeMillis();
        long endTime = startTime + (timeoutSeconds * 1000L);

        // Проверяем сразу
        if (driver.findElements(by).size() > 0) {
            return true;
        }

        // Если не нашли сразу, ждем и проверяем повторно
        while (System.currentTimeMillis() < endTime) {
            try {
                Thread.sleep(500); // Ждем 500мс между проверками
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return false;
            }

            if (driver.findElements(by).size() > 0) {
                return true;
            }
        }

        return false;
    }

    protected void clickIfElementPresent(By by, String error_message, long timeoutInSeconds) {
        if (isElementPresent(by)){
            waitForElementAndClick(by, error_message,timeoutInSeconds);
        } else {
            System.out.println("Element not present, skipping click: \n" + error_message + " \n" + by.toString());
        }
    }

    protected void swipeElementToLeft(By by, String error_message, int timeOfSwipe)
    {
        WebElement element = waitForElementPresent(
                by,
                error_message,
                10
        );
        int left_x = element.getLocation().getX();
        int right_x = left_x + element.getSize().getWidth();
        int upper_y = element.getLocation().getY();
        int lower_y = upper_y + element.getSize().getHeight();
        int middle_y = (upper_y + lower_y) / 2;

        int start_x = right_x -20;
        int end_x = left_x + 20;
        int start_y = middle_y;
        int end_y = middle_y;

        // Выполняем свайп с начальной точки до конечной с заданной продолжительностью.
        this.swipe(
                new Point(start_x, start_y),
                new Point(end_x, end_y),
                Duration.ofMillis(550)
        );
    }

    protected  void swipe (Point start, Point end, Duration duration) {
        // Создаем объект, представляющий палец для выполнения свайпа.
        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        // Создаем последовательность действий для выполнения свайпа.
        Sequence swipe = new Sequence(finger, 1);

        // Добавляем действие для перемещения пальца к начальной точке.
        swipe.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), start.x, start.y));
        // Добавляем действие для нажатия на экран в начальной точке.
        swipe.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        // Добавляем действие для перемещения пальца из начальной точки в конечную в течение заданного времени.
        swipe.addAction(finger.createPointerMove(duration, PointerInput.Origin.viewport(), end.x, end.y));
        // Добавляем действие для отпускания пальца от экрана в конечной точке.
        swipe.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

        // Выполняем последовательность действий (свайп).
        this.driver.perform(Arrays.asList(swipe));
    }

    private int getAmountOfElements(By by)
    {
        List elements = driver.findElements(by);
        return elements.size();
    }

    private void assertElementNotPresent(By by, String error_message){
        int amount_of_elements = getAmountOfElements(by);
        if (amount_of_elements > 0){
            String default_message = "An element '" + by.toString() + "' suppposed to be not present";
            throw new AssertionError(default_message + " " + error_message);
        }
    }

    private String waitForElementAndGetAttribute(By by,
                                                 String attribute,
                                                 String error_message,
                                                 long timeoutInSeconds){
        WebElement element = waitForElementPresent(by, error_message, timeoutInSeconds);
        return element.getAttribute(attribute);
    }
}