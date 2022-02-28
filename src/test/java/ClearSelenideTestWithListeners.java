import com.codeborne.selenide.Condition;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Configuration.*;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

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
