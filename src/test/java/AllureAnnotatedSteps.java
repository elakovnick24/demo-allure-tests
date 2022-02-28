import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Configuration.browserSize;

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
