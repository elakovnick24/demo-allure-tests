![LOGO](https://fs-thb03.getcourse.ru/fileservice/file/thumbnail/h/b635b6cb9478bb87c77e9c070ee6e122.png/s/x50/a/159627/sc/207)

# Allure demo

![allure](https://avatars.githubusercontent.com/u/5879127?s=200&v=4)

*_Lesson topics_*
- Connecting Allure
- Configuring the plugin
- Studying Allure
- Adding script markup (lambda steps)
- Steps with an annotation
- Adding test markup
- Adding parameters to tests
- Allure Extensions
___
## Examples

1. Steps with an annotation

``` java
public class AllureAnnotatedSteps {
    private static final String REPOSITORY = "selenide/selenide";
    private static final int ISSUE_NUMBER = 1687;

    @BeforeAll
    static void beforeAll() {
        browserSize = "1920x1080";
    }

    @Test
    public void testAnnotatedSteps() {
        SelenideLogger.addListener("allure", new AllureSelenide());

        WebStepsTestsAllure steps = new WebStepsTestsAllure();
        steps.openMainPage();
        steps.searchForRepository(REPOSITORY);
        steps.openRepository(REPOSITORY);

        steps.openIssueTab();
        steps.shouldSeeIssueWithNumber(ISSUE_NUMBER);

        steps.takeScreenshot();
    }
}
```

``` java

public class WebStepsTestsAllure {

    @BeforeAll
    static void beforeAll() {
        browserSize = "1920x1080";
    }

    @Step("Opening main page")
    public void openMainPage() {
        open("https://github.com");
    }

    @Step("Searching repo {repo}")
    public void searchForRepository(String repo) {
        $(".header-search-input").click();
        $(".header-search-input").sendKeys(repo);
        $(".header-search-input").submit();
    }

    @Step("Opening repo {repo}")
    public void openRepository(String repo) {
        $(By.linkText(repo)).click();
    }

    @Step("Move to the Issue tab")
    public void openIssueTab() {
        $(By.partialLinkText("Issues")).click();
    }

    @Step("Checking that Issue exist with number {num}")
    public void shouldSeeIssueWithNumber(int num) {
        $(withText("#1687")).should(Condition.exist);
    }

    @Attachment(value = "Screenshot", type = "image/png", fileExtension = "png")
    public byte[] takeScreenshot() {
        return ((TakesScreenshot) WebDriverRunner.getWebDriver()).getScreenshotAs(OutputType.BYTES);
    }
}
```
![Screen1](/src/test/resources/reports/1.png)
___
2. Connecting Allure and configuring the plugin

```java
public class ClearSelenideTestWithListeners {

    @BeforeAll
    static void beforeAll() {
        browserSize = "1920x1080";
    }

    @Test
    public void testIssueSearch() {
        SelenideLogger.addListener("allure", new AllureSelenide());

        open("https://github.com");

        $(".header-search-input").click();
        $(".header-search-input").sendKeys("selenide/selenide");
        $(".header-search-input").submit();

        $(By.linkText("selenide/selenide")).click();
        $(By.partialLinkText("Issues")).click();
        $(withText("#1687")).should(Condition.exist);
    }
}
```

build.gradle 

```java
allure {
    adapter {
        aspectjWeaver.set(true)
        frameworks {
            junit5 {
                adapterVersion.set("2.17.2")
            }
        }

    }
    report {
        version.set("2.17.2")
    }
}
```
![Screen2](/src/test/resources/reports/2.png)
___
3. Adding script markup (lambda steps)

```java
public class LambdaTest {
    private static final String REPOSITORY = "selenide/selenide";
    private static final int ISSUE_NUMBER = 1687;

    @BeforeAll
    static void beforeAll() {
        browserSize = "1920x1080";
    }

    @Test
    public void testLambdaSteps() {
        SelenideLogger.addListener("allure", new AllureSelenide());

        step("Open main page", () -> {
            open("https://github.com");
        });
        step("Searching repo " + REPOSITORY, () -> {
            $(".header-search-input").click();
            $(".header-search-input").sendKeys(REPOSITORY);
            $(".header-search-input").submit();
        });
        step("Opening repo " + REPOSITORY, () -> {
            $(By.linkText("selenide/selenide")).click();
        });
        step("Move to the Issue tab", () -> {
            $(By.partialLinkText("Issues")).click();
            addAttachment("Page Source", "text/html", WebDriverRunner.source(), "html");
        });
        step("Checking that Issue exist with number " + ISSUE_NUMBER, () -> {
            $(withText("#1687")).should(Condition.exist);
        });
    }

}
```
![Screen3](/src/test/resources/reports/3.png)
