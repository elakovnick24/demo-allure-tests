import com.codeborne.selenide.Condition;
import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Configuration.browserSize;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static io.qameta.allure.Allure.addAttachment;
import static io.qameta.allure.Allure.step;

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
