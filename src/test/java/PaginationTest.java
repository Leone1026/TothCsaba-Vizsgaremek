import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Story;
import org.checkerframework.checker.units.qual.A;
import org.example.AboutPage;
import org.example.LandingPage;
import org.example.LoginPage;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.example.PortfolioPage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PaginationTest {
    WebDriver driver;

    LoginPage loginPage;
    LandingPage landingPage;
    PortfolioPage portfolioPage;


    @BeforeEach
    void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = BaseTest.getWebDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        //loginPage = new LoginPage(driver);
    }

    @Test
    @Order(1)
    @Severity(SeverityLevel.NORMAL)
    @Story("PaginationTest")
    @Tag("PortfolioPage")
    @DisplayName("Checking If First Page Is Displayed")
    public void CheckFirstPage() throws InterruptedException {
        loginPage = new LoginPage(driver);
        landingPage = new LandingPage(driver);
        portfolioPage = new PortfolioPage(driver);
        String validUserName = "beckz";
        String validPassword = "30y123";
        loginPage.Navigate();
        loginPage.AcceptTermsAndConditions();
        loginPage.LoginFunction(validUserName, validPassword);
        Assertions.assertEquals("https://lennertamas.github.io/roxo/landing.html", landingPage.GetURL());
        landingPage.GoToPortfolio();
        int actual = portfolioPage.GetPageNumber();
        Assertions.assertEquals(1, actual);
    }

    @Test
    @Order(2)
    @Severity(SeverityLevel.NORMAL)
    @Story("ButtonTest")
    @Tag("PortfolioPage")
    @DisplayName("Checking If Click On Next Button Functions Perfectly")
    public void ClickNextTest() throws InterruptedException {
        loginPage = new LoginPage(driver);
        landingPage = new LandingPage(driver);
        portfolioPage = new PortfolioPage(driver);
        String validUserName = "beckz";
        String validPassword = "30y123";
        loginPage.Navigate();
        loginPage.AcceptTermsAndConditions();
        loginPage.LoginFunction(validUserName, validPassword);
        Assertions.assertEquals("https://lennertamas.github.io/roxo/landing.html", landingPage.GetURL());
        landingPage.GoToPortfolio();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        //Thread.sleep(4000);
        portfolioPage.clickNext();
        //driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        String actual = landingPage.GetURL();
        String expected = "https://lennertamas.github.io/roxo/portfolio/page/2/";
        Assertions.assertEquals(expected, actual);
    }

    @Test
    @Order(3)
    @Severity(SeverityLevel.NORMAL)
    @Story("ButtonTest")
    @Tag("PortfolioPage")
    @DisplayName("Checking If Click On Last Button Functions Perfectly")
    public void GetLastPageNumberTest() throws InterruptedException {
        loginPage = new LoginPage(driver);
        landingPage = new LandingPage(driver);
        portfolioPage = new PortfolioPage(driver);
        String validUserName = "beckz";
        String validPassword = "30y123";
        loginPage.Navigate();
        loginPage.AcceptTermsAndConditions();
        loginPage.LoginFunction(validUserName, validPassword);
        Assertions.assertEquals("https://lennertamas.github.io/roxo/landing.html", landingPage.GetURL());
        landingPage.GoToPortfolio();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        portfolioPage.clickLastPage();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        int actual = portfolioPage.GetPageNumber();
        Assertions.assertEquals(2, actual);
    }

    @Test
    @Order(4)
    @Severity(SeverityLevel.NORMAL)
    @Story("ButtonTest")
    @Tag("PortfolioPage")
    @DisplayName("Checking If Click On Previous Button Functions Perfectly")
    public void ClickPreviousTest() throws InterruptedException {
        loginPage = new LoginPage(driver);
        landingPage = new LandingPage(driver);
        portfolioPage = new PortfolioPage(driver);
        String validUserName = "beckz";
        String validPassword = "30y123";
        loginPage.Navigate();
        loginPage.AcceptTermsAndConditions();
        loginPage.LoginFunction(validUserName, validPassword);
        Assertions.assertEquals("https://lennertamas.github.io/roxo/landing.html", landingPage.GetURL());
        landingPage.GoToPortfolio();
        //driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        //Thread.sleep(4000);
        portfolioPage.clickNext();
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        String actual = landingPage.GetURL();
        String expected = "https://lennertamas.github.io/roxo/portfolio/page/2/";
        Assertions.assertEquals(expected, actual);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        portfolioPage.clickPrevious();
        //driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        String actual2 = landingPage.GetURL();
        String expected2 = "https://lennertamas.github.io/roxo/portfolio/";
        Assertions.assertEquals(expected2, actual2);
    }

    @Test
    @Order(5)
    @Severity(SeverityLevel.NORMAL)
    @Story("ButtonTest")
    @Tag("PortfolioPage")
    @DisplayName("Checking If Click On First Button Functions Perfectly")
    public void GetFirstPageNumberTest() throws InterruptedException {
        loginPage = new LoginPage(driver);
        landingPage = new LandingPage(driver);
        portfolioPage = new PortfolioPage(driver);
        String validUserName = "beckz";
        String validPassword = "30y123";
        loginPage.Navigate();
        loginPage.AcceptTermsAndConditions();
        loginPage.LoginFunction(validUserName, validPassword);
        Assertions.assertEquals("https://lennertamas.github.io/roxo/landing.html", landingPage.GetURL());
        landingPage.GoToPortfolio();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        portfolioPage.clickFirstPage();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        int actual = portfolioPage.GetPageNumber();
        Assertions.assertEquals(1, actual);
    }

    @Test
    @Order(6)
    @Severity(SeverityLevel.NORMAL)
    @Story("ButtonTest")
    @Tag("PortfolioPage")
    @DisplayName("Checking If Click On First Page Button Directs To First Page")
    public void ClickFirstPageTest() throws InterruptedException {
        loginPage = new LoginPage(driver);
        landingPage = new LandingPage(driver);
        portfolioPage = new PortfolioPage(driver);
        String validUserName = "beckz";
        String validPassword = "30y123";
        loginPage.Navigate();
        loginPage.AcceptTermsAndConditions();
        loginPage.LoginFunction(validUserName, validPassword);
        Assertions.assertEquals("https://lennertamas.github.io/roxo/landing.html", landingPage.GetURL());
        landingPage.GoToPortfolio();
        portfolioPage.clickNext();
        driver.manage().timeouts().implicitlyWait(6, TimeUnit.SECONDS);
        String actual = landingPage.GetURL();
        String expected = "https://lennertamas.github.io/roxo/portfolio/page/2/";
        Assertions.assertEquals(expected, actual);
        portfolioPage.clickFirstPage();
        String actual2 = landingPage.GetURL();
        String expected2 = "https://lennertamas.github.io/roxo/portfolio/";
        Assertions.assertEquals(expected2, actual2);
    }


    @Test
    @Order(7)
    @Severity(SeverityLevel.NORMAL)
    @Story("PaginationTest")
    @Tag("PortfolioPage")
    @DisplayName("Get The Maximum Number Of Pages With Another Method")
    public void GetLastPageNumberTest2() throws InterruptedException {
        loginPage = new LoginPage(driver);
        landingPage = new LandingPage(driver);
        portfolioPage = new PortfolioPage(driver);
        String validUserName = "beckz";
        String validPassword = "30y123";
        int maxPageNumber = 100;
        loginPage.Navigate();
        loginPage.AcceptTermsAndConditions();
        loginPage.LoginFunction(validUserName, validPassword);
        Assertions.assertEquals("https://lennertamas.github.io/roxo/landing.html", landingPage.GetURL());
        landingPage.GoToPortfolio();
        int actual = portfolioPage.GetPageNumberMax(maxPageNumber);
        Assertions.assertEquals(2, actual);
    }

    @Test
    @Order(8)
    @Severity(SeverityLevel.MINOR)
    @Story("ButtonTest")
    @Tag("PortfolioPage")
    @DisplayName("Checking if Next Button is enabled on Last Page")
    public void CheckNextButtonOnLastPageTest() throws InterruptedException {
        loginPage = new LoginPage(driver);
        landingPage = new LandingPage(driver);
        portfolioPage = new PortfolioPage(driver);
        String validUserName = "beckz";
        String validPassword = "30y123";
        loginPage.Navigate();
        loginPage.AcceptTermsAndConditions();
        loginPage.LoginFunction(validUserName, validPassword);
        Assertions.assertEquals("https://lennertamas.github.io/roxo/landing.html", landingPage.GetURL());
        landingPage.GoToPortfolio();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        portfolioPage.clickLastPage();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        boolean actual = portfolioPage.GoToNextButton();
        Assertions.assertFalse(actual);
    }

    @Test
    @Order(9)
    @Severity(SeverityLevel.MINOR)
    @Story("ButtonTest")
    @Tag("PortfolioPage")
    @DisplayName("Checking if Last Button is enabled on Last Page")
    public void CheckLastButtonOnLastPageTest() throws InterruptedException {
        loginPage = new LoginPage(driver);
        landingPage = new LandingPage(driver);
        portfolioPage = new PortfolioPage(driver);
        String validUserName = "beckz";
        String validPassword = "30y123";
        loginPage.Navigate();
        loginPage.AcceptTermsAndConditions();
        loginPage.LoginFunction(validUserName, validPassword);
        Assertions.assertEquals("https://lennertamas.github.io/roxo/landing.html", landingPage.GetURL());
        landingPage.GoToPortfolio();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        portfolioPage.clickLastPage();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        boolean actual = portfolioPage.GoToLastButton();
        Assertions.assertFalse(actual);
    }

    @Test
    @Order(10)
    @Severity(SeverityLevel.MINOR)
    @Story("ButtonTest")
    @Tag("PortfolioPage")
    @DisplayName("Checking if Previous Button is enabled on First Page")
    public void CheckPreviousButtonOnFirstPageTest() throws InterruptedException {
        loginPage = new LoginPage(driver);
        landingPage = new LandingPage(driver);
        portfolioPage = new PortfolioPage(driver);
        String validUserName = "beckz";
        String validPassword = "30y123";
        loginPage.Navigate();
        loginPage.AcceptTermsAndConditions();
        loginPage.LoginFunction(validUserName, validPassword);
        Assertions.assertEquals("https://lennertamas.github.io/roxo/landing.html", landingPage.GetURL());
        landingPage.GoToPortfolio();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        portfolioPage.clickFirstPage();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        boolean actual = portfolioPage.GoToPreviousButton();
        Assertions.assertFalse(actual);
    }

    @Test
    @Order(11)
    @Severity(SeverityLevel.MINOR)
    @Story("ButtonTest")
    @Tag("PortfolioPage")
    @DisplayName("Checking if First Button is enabled on First Page")
    public void CheckFirstButtonOnFirstPageTest() throws InterruptedException {
        loginPage = new LoginPage(driver);
        landingPage = new LandingPage(driver);
        portfolioPage = new PortfolioPage(driver);
        String validUserName = "beckz";
        String validPassword = "30y123";
        loginPage.Navigate();
        loginPage.AcceptTermsAndConditions();
        loginPage.LoginFunction(validUserName, validPassword);
        Assertions.assertEquals("https://lennertamas.github.io/roxo/landing.html", landingPage.GetURL());
        landingPage.GoToPortfolio();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        portfolioPage.clickFirstPage();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        boolean actual = portfolioPage.GoToFirstButton();
        Assertions.assertFalse(actual);
    }

    @Test
    @Order(12)
    @Severity(SeverityLevel.NORMAL)
    @Story("PaginationTest")
    @Tag("PortfolioPage")
    @DisplayName("Getting The Number Of Products While Iterating Over Pages")
    public void PaginationTest() throws InterruptedException {
        loginPage = new LoginPage(driver);
        landingPage = new LandingPage(driver);
        portfolioPage = new PortfolioPage(driver);
        String validUserName = "beckz";
        String validPassword = "30y123";
        loginPage.Navigate();
        loginPage.AcceptTermsAndConditions();
        loginPage.LoginFunction(validUserName, validPassword);
        Assertions.assertEquals("https://lennertamas.github.io/roxo/landing.html", landingPage.GetURL());
        landingPage.GoToPortfolio();
        int maxPageNumber = 10;
        int actual = portfolioPage.GetProductNumber(maxPageNumber);
        Assertions.assertEquals(5, actual);
    }

    @Test
    @Order(13)
    @Severity(SeverityLevel.NORMAL)
    @Story("PaginationTest")
    @Tag("PortfolioPage")
    @DisplayName("Getting The Name Of Products While Iterating Over Pages")
    public void PaginationTest2() throws InterruptedException {
        loginPage = new LoginPage(driver);
        landingPage = new LandingPage(driver);
        portfolioPage = new PortfolioPage(driver);
        String validUserName = "beckz";
        String validPassword = "30y123";
        loginPage.Navigate();
        loginPage.AcceptTermsAndConditions();
        loginPage.LoginFunction(validUserName, validPassword);
        Assertions.assertEquals("https://lennertamas.github.io/roxo/landing.html", landingPage.GetURL());
        landingPage.GoToPortfolio();
        int maxPageNumber = 10;
        List<String> expected = new ArrayList<>(Arrays.asList("KIO-TAPE BRAND", "USE-LESS BRAND", "OSEN CLOCK", "SEAMLESS WATCH", "KIO TAPE"));
        List<String> actual = portfolioPage.ListProductsName(maxPageNumber);
        Assertions.assertEquals(expected, actual);
    }

    @AfterEach
    public void TearDown () {
        driver.quit();
    }

}
