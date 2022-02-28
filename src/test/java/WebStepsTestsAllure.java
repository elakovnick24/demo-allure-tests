import com.codeborne.selenide.Condition;
import com.codeborne.selenide.WebDriverRunner;
import io.qameta.allure.Attachment;
import io.qameta.allure.Step;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import static com.codeborne.selenide.Configuration.browserSize;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

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
