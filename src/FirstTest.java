import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;

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

        waitForElementAndClick(
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
    public void testCancelSearch()
    {
        waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find 'Search Wikipedia' field",
                5
        );

        waitForElementAndClick(
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
    public void testCompareArticleTitle()
    {
        waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find 'Search Wikipedia' field",
                5
        );

        waitForElementAndClick(
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
    public void testAssertElementHasText()
    {
        waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find 'Search Wikipedia' field",
                5
        );

        waitForElementAndClick(
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
    public void testCheckResultAndCancelSearch()
    {
        waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find 'Search Wikipedia' field",
                5
        );

        //Закрываем онбординг
        waitForElementAndClick(
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

        //Проверяем, что список результато отображается
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


    private WebElement waitForElementAndClick(By by, String error_message, long timeoutInSeconds)
    {
        WebElement element = waitForElementPresent(by, error_message, 5);
        element.click();
        return element;
    }



    private WebElement waitForElementAndSendKeys(By by, String value, String error_message, long timeoutInSeconds)
    {
        WebElement element = waitForElementPresent(by, error_message, 15);
        element.sendKeys(value);
        return element;
    }

    private boolean waitForElementNotPresent(By by, String error_message, long timeoutInSeconds)
    {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(error_message + "\n");
        return wait.until(
                ExpectedConditions.invisibilityOfElementLocated(by)
    );
    }
    private WebElement waitForElementAndClear(By by, String error_message, long timeoutInSeconds)
    {
        WebElement element = waitForElementPresent(by, error_message, timeoutInSeconds);
        element.clear();
        return element;
    }

    //Homework Lesson 3 Ex2
    private WebElement assertElementHasText(By by, String expected_text, String error_message)
    {
        WebElement element = waitForElementPresent(by,error_message);
        String element_text = element.getAttribute("text");
        Assert.assertEquals(error_message, expected_text, element_text);
        return element;
    }



}