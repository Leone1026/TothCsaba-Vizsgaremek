import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.*;
import org.checkerframework.checker.units.qual.A;
import org.example.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.*;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.ByteArrayInputStream;
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
    BlogPage blogPage;


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
    @DisplayName("TC15 - Checking If First Page Is Displayed")
    @Description("Verify that when user clicks on Portfolio, the first page appears")
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
    @DisplayName("TC16 - Checking If Click On Next Button Functions Perfectly")
    @Description("Verify that when user clicks on Next Button, the website loads the next page.")
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
    @DisplayName("TC17 - Checking Last Page Button")
    @Description("Verify that when user clicks on Last Page Button, the website loads the last page.")
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
        Allure.addAttachment("TC17 - Screenshot", new ByteArrayInputStream(((TakesScreenshot)driver).getScreenshotAs(OutputType.BYTES)));
        Assertions.assertEquals(2, actual);
    }

    @Test
    @Order(4)
    @Severity(SeverityLevel.NORMAL)
    @Story("ButtonTest")
    @Tag("PortfolioPage")
    @DisplayName("TC18 - Checking Previous Button")
    @Description("Verify that when user clicks on Previous Button, the website loads the previous page.")
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
        portfolioPage.clickNext();
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        String actual = landingPage.GetURL();
        String expected = "https://lennertamas.github.io/roxo/portfolio/page/2/";
        Allure.addAttachment("TC18 - Screenshot 01", new ByteArrayInputStream(((TakesScreenshot)driver).getScreenshotAs(OutputType.BYTES)));
        Assertions.assertEquals(expected, actual);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        portfolioPage.clickPrevious();
        String actual2 = landingPage.GetURL();
        String expected2 = "https://lennertamas.github.io/roxo/portfolio/";
        Allure.addAttachment("TC18 - Screenshot 02", new ByteArrayInputStream(((TakesScreenshot)driver).getScreenshotAs(OutputType.BYTES)));
        Assertions.assertEquals(expected2, actual2);
    }

    @Test
    @Order(5)
    @Severity(SeverityLevel.NORMAL)
    @Story("ButtonTest")
    @Tag("PortfolioPage")
    @DisplayName("TC19 - Checking First Page Button")
    @Description("Verify that when user clicks on First Page Button, the website loads the first page.")
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
    @DisplayName("TC20 - Checking First Page Button On Another Page")
    @Description("Verify that when user clicks on First Page Button on the Second Page, the website loads the first page.")
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
    @DisplayName("TC21 - Get The Maximum Number Of Pages With Pagination")
    @Description("Verify that Pagination function works when user wants to get the maximum number of Pages.")
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
        Allure.addAttachment("TC21 - Screenshot", new ByteArrayInputStream(((TakesScreenshot)driver).getScreenshotAs(OutputType.BYTES)));
        Assertions.assertEquals(2, actual);
    }

    @Test
    @Order(8)
    @Severity(SeverityLevel.MINOR)
    @Story("ButtonTest")
    @Tag("PortfolioPage")
    @DisplayName("TC22 - Checking if Next Button is enabled on Last Page")
    @Description("Verify that user cannot click on Next Button on Last Page")
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
        Allure.addAttachment("TC22 - Screenshot", new ByteArrayInputStream(((TakesScreenshot)driver).getScreenshotAs(OutputType.BYTES)));
        Assertions.assertFalse(actual);
    }

    @Test
    @Order(9)
    @Severity(SeverityLevel.MINOR)
    @Story("ButtonTest")
    @Tag("PortfolioPage")
    @DisplayName("TC23 - Checking if Last Page Button is enabled on Last Page")
    @Description("Verify that user cannot click on Last Page Button on Last Page")
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
        Allure.addAttachment("TC23 - Screenshot", new ByteArrayInputStream(((TakesScreenshot)driver).getScreenshotAs(OutputType.BYTES)));
        Assertions.assertFalse(actual);
    }

    @Test
    @Order(10)
    @Severity(SeverityLevel.MINOR)
    @Story("ButtonTest")
    @Tag("PortfolioPage")
    @DisplayName("TC24 - Checking if Previous Button is enabled on First Page")
    @Description("Verify that user cannot click on Previous Button on First Page")
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
        Allure.addAttachment("TC24 - Screenshot", new ByteArrayInputStream(((TakesScreenshot)driver).getScreenshotAs(OutputType.BYTES)));
        Assertions.assertFalse(actual);
    }

    @Test
    @Order(11)
    @Severity(SeverityLevel.MINOR)
    @Story("ButtonTest")
    @Tag("PortfolioPage")
    @DisplayName("TC25 - Checking if First Button is enabled on First Page")
    @Description("Verify that user cannot click on First Page Button on First Page")
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
        Allure.addAttachment("TC25 - Screenshot", new ByteArrayInputStream(((TakesScreenshot)driver).getScreenshotAs(OutputType.BYTES)));
        Assertions.assertFalse(actual);
    }

    @Test
    @Order(12)
    @Severity(SeverityLevel.NORMAL)
    @Story("PaginationTest")
    @Tag("PortfolioPage")
    @DisplayName("TC26 - Getting The Number Of Products While Iterating Over Pages")
    @Description("Verify that Number of Products matches the requirements")
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
    @DisplayName("TC27 - Getting The Name Of Products While Iterating Over Pages")
    @Description("Verify that names of products matches the predetermined data")
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
        Allure.addAttachment("TC27 - Screenshot", new ByteArrayInputStream(((TakesScreenshot)driver).getScreenshotAs(OutputType.BYTES)));
        Assertions.assertEquals(expected, actual);
    }

    @Test
    @Order(14)
    @Severity(SeverityLevel.NORMAL)
    @Story("PaginationTest")
    @Tag("BlogPage")
    @DisplayName("TC28 - Getting The Number Of Blog Entries While Iterating Over Pages")
    @Description("Verify that Number of Blog Entries matches the requirements")
    public void PaginationTest3() throws InterruptedException {
        loginPage = new LoginPage(driver);
        landingPage = new LandingPage(driver);
        blogPage = new BlogPage(driver);
        String validUserName = "beckz";
        String validPassword = "30y123";
        loginPage.Navigate();
        loginPage.AcceptTermsAndConditions();
        loginPage.LoginFunction(validUserName, validPassword);
        Assertions.assertEquals("https://lennertamas.github.io/roxo/landing.html", landingPage.GetURL());
        landingPage.GoToBlog();
        int maxPageNumber = 10;
        int actual = blogPage.GetBlogNumber(maxPageNumber);
        Allure.addAttachment("TC28 - Screenshot", new ByteArrayInputStream(((TakesScreenshot)driver).getScreenshotAs(OutputType.BYTES)));
        Assertions.assertEquals(5, actual);
    }

    @AfterEach
    public void TearDown () {
        driver.quit();
    }

}
